package com.example.cshop.mappers;

import com.example.cshop.dtos.order.OrderCreateDto;
import com.example.cshop.dtos.order.OrderDto;
import com.example.cshop.dtos.order.OrderUpdateDto;
import com.example.cshop.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "user.username", target = "username")
    OrderDto toDto(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    Order toEntity(OrderCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "total", ignore = true)
    void updateEntity(OrderUpdateDto dto, @MappingTarget Order order);
}
