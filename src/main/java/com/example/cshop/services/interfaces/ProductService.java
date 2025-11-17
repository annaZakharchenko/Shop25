package com.example.cshop.services.interfaces;

import com.example.cshop.dtos.product.ProductCreateDto;
import com.example.cshop.dtos.product.ProductDto;
import com.example.cshop.dtos.product.ProductUpdateDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();

    ProductDto findById(Long id);

    ProductDto create(ProductCreateDto dto);

    ProductDto update(Long id, ProductUpdateDto dto);

    void delete(Long id);
}
