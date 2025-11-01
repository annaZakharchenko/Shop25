package com.example.cshop.dtos.order;

import com.example.cshop.models.OrderStatus;

public class OrderUpdateDto {

    private OrderStatus status;

    public OrderUpdateDto() {}

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}
