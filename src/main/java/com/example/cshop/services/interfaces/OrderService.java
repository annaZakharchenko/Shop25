package com.example.cshop.services.interfaces;

import com.example.cshop.dtos.order.OrderCreateDto;
import com.example.cshop.dtos.order.OrderDto;
import com.example.cshop.dtos.order.OrderUpdateDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface OrderService {
    List<OrderDto> findAll();
    OrderDto findById(Long id);
    OrderDto create(OrderCreateDto dto);

    @Transactional
    OrderDto createOrderFromCart(String userEmail, Map<Long, Integer> cart);

    OrderDto update(Long id, OrderUpdateDto dto);
    void delete(Long id);


    List<OrderDto> getOrdersForUserById(Long userId);

    List<OrderDto> getOrdersForUserByEmail(String email);
}
