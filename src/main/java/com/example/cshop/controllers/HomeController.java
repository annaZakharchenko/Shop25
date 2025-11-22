package com.example.cshop.controllers;

import com.example.cshop.dtos.category.CategoryDto;
import com.example.cshop.dtos.product.ProductDto;
import com.example.cshop.services.interfaces.CategoryService;
import com.example.cshop.services.interfaces.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public HomeController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping({"/", "/home"})
    public String home(@RequestParam(required = false) Long categoryId,
                       Model model,
                       Authentication authentication) {

        // --- Продукты ---
        List<ProductDto> products;
        CategoryDto selectedCategory = null;
        if (categoryId != null) {
            products = productService.findByCategoryId(categoryId);
            selectedCategory = categoryService.findById(categoryId);
        } else {
            products = productService.findAll();
        }

        // --- Категории ---
        List<CategoryDto> categories = categoryService.findAll();

        // --- Добавляем атрибуты для Thymeleaf ---
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("category", selectedCategory); // может быть null

        // --- Проверка авторизации ---
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("isAuthenticated", true);
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            model.addAttribute("isAdmin", isAdmin);
        } else {
            model.addAttribute("isAuthenticated", false);
            model.addAttribute("isAdmin", false);
        }

        return "home";
    }
}
