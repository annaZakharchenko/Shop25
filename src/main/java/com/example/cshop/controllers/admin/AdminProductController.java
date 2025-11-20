package com.example.cshop.controllers.admin;

import com.example.cshop.dtos.product.ProductCreateDto;
import com.example.cshop.services.interfaces.CategoryService;
import com.example.cshop.services.interfaces.ProductService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import com.example.cshop.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public AdminProductController(CategoryService categoryService, ProductService productService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/products/products-list";
    }

    @GetMapping("/products/create")
    public String showCreateProductForm(Model model) {
        model.addAttribute("product", new ProductCreateDto());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product-create";
    }

    @PostMapping("/products/create")
    public String createProduct(@ModelAttribute("product") @Valid ProductCreateDto dto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "admin/product-create";
        }
        productService.create(dto);
        return "redirect:/admin";
    }

}
