package com.example.cshop.mappers;

import com.example.cshop.dtos.category.CategoryCreateDto;
import com.example.cshop.dtos.category.CategoryDto;
import com.example.cshop.dtos.category.CategoryUpdateDto;
import com.example.cshop.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryCreateDto dto);
    void updateEntity(CategoryUpdateDto dto, @MappingTarget Category category);
}
