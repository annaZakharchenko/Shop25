package com.example.cshop.mappers;

import com.example.cshop.dtos.product.ProductCreateDto;
import com.example.cshop.dtos.product.ProductDto;
import com.example.cshop.dtos.product.ProductUpdateDto;
import com.example.cshop.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductCreateDto dto);
    void updateEntity(ProductUpdateDto dto, @MappingTarget Product product);
}
