package com.example.cshop.mappers;

import com.example.cshop.dtos.orderitemdto.OrderItemCreateDto;
import com.example.cshop.dtos.orderitemdto.OrderItemDto;
import com.example.cshop.dtos.orderitemdto.OrderItemUpdateDto;
import com.example.cshop.models.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItem item);
    OrderItem toEntity(OrderItemCreateDto dto);
    void updateEntity(OrderItemUpdateDto dto, @MappingTarget OrderItem item);
}
