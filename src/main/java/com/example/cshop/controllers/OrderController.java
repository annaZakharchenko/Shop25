package com.example.cshop.controllers;

import com.example.cshop.dtos.order.OrderCreateDto;
import com.example.cshop.dtos.order.OrderDto;
import com.example.cshop.dtos.order.OrderUpdateDto;
import com.example.cshop.services.interfaces.OrderService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "order/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("order", new OrderCreateDto());
        return "order/create";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute("order") @Valid OrderCreateDto dto,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "order/create";
        }
        orderService.create(dto);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        OrderDto dto = orderService.findById(id);
        OrderUpdateDto updateDto = new OrderUpdateDto();
        updateDto.setStatus(dto.getStatus());
        model.addAttribute("order", updateDto);
        model.addAttribute("id", id);
        return "order/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateOrder(@PathVariable Long id,
                              @ModelAttribute("order") @Valid OrderUpdateDto dto,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "order/edit";
        }
        orderService.update(id, dto);
        return "redirect:/orders";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/orders";
    }
}
