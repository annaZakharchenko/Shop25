package com.example.cshop.services.interfaces;

import com.example.cshop.dtos.order.OrderCreateDto;
import com.example.cshop.dtos.order.OrderDto;
import com.example.cshop.dtos.order.OrderUpdateDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> findAll();
    OrderDto findById(Long id);
    OrderDto create(OrderCreateDto dto);
    OrderDto update(Long id, OrderUpdateDto dto);
    void delete(Long id);
}
