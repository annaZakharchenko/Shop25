package com.example.cshop.controllers;

import com.example.cshop.models.Product;
import com.example.cshop.services.interfaces.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Страница с деталями продукта
    @GetMapping("/products/detail/{id}")
    public String productDetail(@PathVariable Long id, Model model, HttpSession session) {

        var productDto = productService.findById(id);
        model.addAttribute("product", productDto);

        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        int count = 0;
        if (cart != null) {
            for (int q : cart.values()) count += q;
        }
        model.addAttribute("cartItemCount", count);

        return "product-detail";
    }

}
