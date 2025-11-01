package com.example.cshop.dtos.orderitemdto;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class OrderItemUpdateDto {

    @Positive
    private Integer quantity;

    private BigDecimal unitPrice;

    public OrderItemUpdateDto() {}

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}
