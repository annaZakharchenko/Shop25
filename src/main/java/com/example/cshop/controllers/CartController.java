package com.example.cshop.controllers;

import com.example.cshop.dtos.orderitemdto.OrderItemDto;
import com.example.cshop.dtos.product.ProductDto;
import com.example.cshop.services.interfaces.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"","/"})
    public String showCart(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }

        List<OrderItemDto> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            ProductDto product = productService.findById(entry.getKey());

            OrderItemDto orderItem = new OrderItemDto();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setQuantity(entry.getValue());

            items.add(orderItem);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }

        model.addAttribute("items", items);
        model.addAttribute("total", total);

        return "user/cart"; // путь к твоему Thymeleaf шаблону
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
        }

        // Добавляем количество
        cart.put(productId, cart.getOrDefault(productId, 0) + quantity);
        session.setAttribute("cart", cart);

        return "redirect:/";
    }


    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId, HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");

        if (cart != null) {
            cart.remove(productId);
            session.setAttribute("cart", cart);
        }

        return "redirect:/cart";
    }

    @PostMapping("/update/{productId}")
    @ResponseBody
    public Map<String, Object> updateCartQuantityAjax(@PathVariable Long productId,
                                                      @RequestParam int quantity,
                                                      HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");

        Map<String, Object> result = new HashMap<>();

        if (cart != null && quantity > 0) {
            cart.put(productId, quantity);
            session.setAttribute("cart", cart);

            ProductDto product = productService.findById(productId);
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));

            BigDecimal total = BigDecimal.ZERO;
            for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
                ProductDto p = productService.findById(entry.getKey());
                total = total.add(p.getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
            }

            result.put("success", true);
            result.put("subtotal", subtotal);
            result.put("total", total);
        } else {
            result.put("success", false);
        }

        return result;
    }




    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/cart/";
    }
}