package com.example.cshop.controllers.admin;

import com.example.cshop.dtos.product.ProductCreateDto;
import com.example.cshop.dtos.product.ProductDto;
import com.example.cshop.dtos.product.ProductUpdateDto;
import com.example.cshop.mappers.ProductMapper;
import com.example.cshop.services.interfaces.CategoryService;
import com.example.cshop.services.interfaces.ProductService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import com.example.cshop.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductMapper  productMapper;

    public AdminProductController(CategoryService categoryService, ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }
    @GetMapping("/")
    public String listProducts(Model model) {
        var products = productService.findAll();
        System.out.println(products); // для дебага
        model.addAttribute("products", products);
        return "admin/products/products-list";
    }


    @GetMapping("/create")
    public String showCreateProductForm(Model model) {
        model.addAttribute("product", new ProductCreateDto());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/products/product-create";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute("product") @Valid ProductCreateDto dto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "admin/products/product-create";
        }
        productService.create(dto);
        return "redirect:/admin/products/";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        ProductDto productDto = productService.findById(id);
        ProductUpdateDto updateDto = productMapper.toUpdateDto(productDto);
        model.addAttribute("product", updateDto);
        model.addAttribute("categories", categoryService.findAll());
        return "admin/products/product-update";
    }


    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id,
                                @ModelAttribute("product") @Valid ProductUpdateDto dto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "admin/products/product-update";
        }
        productService.update(id, dto);
        return "redirect:/admin/products/";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/admin/products/";
    }

}
