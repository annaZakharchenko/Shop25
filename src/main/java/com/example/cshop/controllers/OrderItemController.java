package com.example.cshop.controllers;

import com.example.cshop.dtos.orderitemdto.OrderItemCreateDto;
import com.example.cshop.dtos.orderitemdto.OrderItemDto;
import com.example.cshop.dtos.orderitemdto.OrderItemUpdateDto;
import com.example.cshop.services.interfaces.OrderItemService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public String listOrderItems(Model model) {
        model.addAttribute("orderItems", orderItemService.findAll());
        return "orderItem/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("orderItem", new OrderItemCreateDto());
        return "orderItem/create";
    }

    @PostMapping("/create")
    public String createOrderItem(@ModelAttribute("orderItem") @Valid OrderItemCreateDto dto,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orderItem/create";
        }
        orderItemService.create(dto);
        return "redirect:/order-items";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        OrderItemDto dto = orderItemService.findById(id);
        OrderItemUpdateDto updateDto = new OrderItemUpdateDto();
        updateDto.setQuantity(dto.getQuantity());
        updateDto.setUnitPrice(dto.getUnitPrice());
        model.addAttribute("orderItem", updateDto);
        model.addAttribute("id", id);
        return "orderItem/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateOrderItem(@PathVariable Long id,
                                  @ModelAttribute("orderItem") @Valid OrderItemUpdateDto dto,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orderItem/edit";
        }
        orderItemService.update(id, dto);
        return "redirect:/order-items";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrderItem(@PathVariable Long id) {
        orderItemService.delete(id);
        return "redirect:/order-items";
    }
}
