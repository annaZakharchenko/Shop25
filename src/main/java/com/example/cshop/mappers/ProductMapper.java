package com.example.cshop.mappers;

import com.example.cshop.dtos.product.ProductCreateDto;
import com.example.cshop.dtos.product.ProductDto;
import com.example.cshop.dtos.product.ProductUpdateDto;
import com.example.cshop.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    @Mapping(source = "category", target = "category") // добавляем маппинг категории
    ProductDto toDto(Product product);

    Product toEntity(ProductCreateDto dto);

    void updateEntity(ProductUpdateDto dto, @MappingTarget Product product);
}
