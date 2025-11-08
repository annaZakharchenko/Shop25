package com.example.cshop.controllers;

import com.example.cshop.services.interfaces.CategoryService;
import com.example.cshop.services.interfaces.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public HomeController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model, Authentication auth) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", productService.findAll());

        boolean isAuthenticated = auth != null && auth.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        boolean isAdmin = isAuthenticated && auth.getName().equals("adminanna@gmail.com");
        model.addAttribute("isAdmin", isAdmin);

        return "home";
    }

}
