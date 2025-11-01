package com.example.cshop.dtos.order;

import com.example.cshop.dtos.orderitemdto.OrderItemCreateDto;

import java.util.List;

public class OrderCreateDto {

    private Long userId;
    private List<OrderItemCreateDto> items;

    public OrderCreateDto() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public List<OrderItemCreateDto> getItems() { return items; }
    public void setItems(List<OrderItemCreateDto> items) { this.items = items; }
}
