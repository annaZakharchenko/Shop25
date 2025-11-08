package com.example.cshop.services.interfaces;

import com.example.cshop.dtos.orderitemdto.OrderItemCreateDto;
import com.example.cshop.dtos.orderitemdto.OrderItemDto;
import com.example.cshop.dtos.orderitemdto.OrderItemUpdateDto;

import java.util.List;

public interface OrderItemService {
    List<OrderItemDto> findAll();
    OrderItemDto findById(Long id);
    OrderItemDto create(OrderItemCreateDto dto);
    OrderItemDto update(Long id, OrderItemUpdateDto dto);
    void delete(Long id);
}
