package com.example.cshop.mappers;

import com.example.cshop.dtos.product.ProductCreateDto;
import com.example.cshop.dtos.product.ProductDto;
import com.example.cshop.dtos.product.ProductUpdateDto;
import com.example.cshop.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    ProductUpdateDto toUpdateDto(ProductDto dto);
    Product toEntity(ProductCreateDto dto);
    void updateEntity(ProductUpdateDto dto, @MappingTarget Product product);
}

