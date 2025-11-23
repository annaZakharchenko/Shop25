package com.example.cshop.controllers;

import com.example.cshop.models.Product;
import com.example.cshop.services.interfaces.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Страница с деталями продукта
    @GetMapping("/products/detail/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        var productDto = productService.findById(id); // ProductDto
        model.addAttribute("product", productDto);
        return "product-detail"; // thymeleaf страница product-detail.html
    }
}
