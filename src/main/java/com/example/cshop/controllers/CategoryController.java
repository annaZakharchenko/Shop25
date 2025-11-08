package com.example.cshop.controllers;

import com.example.cshop.dtos.category.CategoryCreateDto;
import com.example.cshop.dtos.category.CategoryDto;
import com.example.cshop.dtos.category.CategoryUpdateDto;
import com.example.cshop.services.interfaces.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new CategoryCreateDto());
        return "category/create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute("category") @Valid CategoryCreateDto dto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "category/create";
        }
        categoryService.create(dto);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CategoryDto dto = categoryService.findById(id);
        CategoryUpdateDto updateDto = new CategoryUpdateDto();
        updateDto.setName(dto.getName());
        model.addAttribute("category", updateDto);
        model.addAttribute("id", id);
        return "category/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id,
                                 @ModelAttribute("category") @Valid CategoryUpdateDto dto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "category/edit";
        }
        categoryService.update(id, dto);
        return "redirect:/categories";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }
}
