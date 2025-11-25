package com.example.cshop.controllers.admin;

import com.example.cshop.dtos.category.CategoryCreateDto;
import com.example.cshop.dtos.category.CategoryDto;
import com.example.cshop.dtos.category.CategoryUpdateDto;
import com.example.cshop.services.interfaces.CategoryService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import com.example.cshop.repositories.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/categories/categories-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new CategoryCreateDto());
        return "admin/categories/category-create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute("category") @Valid CategoryCreateDto dto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/categories/category-create";
        }
        categoryService.create(dto);
        return "redirect:/admin/categories/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CategoryDto dto = categoryService.findById(id);
        CategoryUpdateDto updateDto = new CategoryUpdateDto();
        updateDto.setName(dto.getName());
        updateDto.setDescription(dto.getDescription());
        model.addAttribute("category", updateDto);
        model.addAttribute("id", id);
        return "admin/categories/category-update";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id,
                                 @ModelAttribute("category") @Valid CategoryUpdateDto dto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/categories/category-update";
        }
        categoryService.update(id, dto);
        return "redirect:/admin/categories/";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes ra) {
        try {
            categoryService.delete(id);
            ra.addFlashAttribute("success", "Category deleted successfully!");
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", "Category cannot be deleted because it has products assigned.");
        }
        return "redirect:/admin/categories/";
    }


}


