package com.example.cshop.controllers;

import com.example.cshop.dtos.category.CategoryCreateDto;
import com.example.cshop.dtos.product.ProductCreateDto;
import com.example.cshop.services.interfaces.CategoryService;
import com.example.cshop.services.interfaces.OrderService;
import com.example.cshop.services.interfaces.ProductService;
import com.example.cshop.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final OrderService orderService;
    private final UserService userService;

    public AdminController(ProductService productService,
                           CategoryService categoryService,
                           OrderService orderService,
                           UserService userService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.userService = userService;
    }

    // Dashboard
    @GetMapping("/dashboard")
    public String adminDashboard(Model model, Authentication auth) {
        model.addAttribute("username", auth.getName());
        model.addAttribute("totalProducts", productService.findAll().size());
        model.addAttribute("totalCategories", categoryService.findAll().size());
        model.addAttribute("totalOrders", orderService.findAll().size());
        model.addAttribute("totalUsers", userService.findAll().size());
        return "admin/dashboard";
    }

    // === ORDER MANAGEMENT ===
    @GetMapping("/orders")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "admin/orders";
    }

    @GetMapping("/orders/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        return "admin/order-detail";
    }

    @PostMapping("/orders/{id}/update-status")
    public String updateOrderStatus(@PathVariable Long id,
                                    @RequestParam("status") String status) {
        var order = orderService.findById(id);
        var updateDto = new com.example.cshop.dtos.order.OrderUpdateDto();
        updateDto.setStatus(com.example.cshop.models.OrderStatus.valueOf(status));
        orderService.update(id, updateDto);
        return "redirect:/admin/orders/" + id;
    }
}