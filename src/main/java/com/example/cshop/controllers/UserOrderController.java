package com.example.cshop.controllers;

import com.example.cshop.dtos.order.OrderDto;
import com.example.cshop.mappers.OrderMapper;
import com.example.cshop.repositories.OrderRepository;
import com.example.cshop.services.interfaces.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserOrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public UserOrderController(OrderService orderService, OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @GetMapping("/myorders")
    public String myOrders(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<OrderDto> orders = orderRepository.findByUserUsername(username)
                .stream()
                .map(orderMapper::toDto)
                .toList();

        model.addAttribute("orders", orders);
        return "user/my-orders";
    }

}
