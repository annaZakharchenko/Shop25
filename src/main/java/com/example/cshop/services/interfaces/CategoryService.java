package com.example.cshop.services.interfaces;

import com.example.cshop.dtos.category.CategoryCreateDto;
import com.example.cshop.dtos.category.CategoryDto;
import com.example.cshop.dtos.category.CategoryUpdateDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();
    CategoryDto findById(Long id);
    CategoryDto create(CategoryCreateDto dto);
    CategoryDto update(Long id, CategoryUpdateDto dto);
    void delete(Long id);
}
