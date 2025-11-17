package com.example.cshop.controllers;

import com.example.cshop.dtos.user.UserCreateDto;
import com.example.cshop.dtos.user.UserDto;
import com.example.cshop.dtos.user.UserUpdateDto;
import com.example.cshop.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new UserCreateDto());
        return "user/create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") @Valid UserCreateDto dto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/create";
        }
        userService.create(dto);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        UserDto dto = userService.findById(id);
        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setUsername(dto.getUsername());
        updateDto.setEmail(dto.getEmail());
        model.addAttribute("user", updateDto);
        model.addAttribute("id", id);
        return "user/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute("user") @Valid UserUpdateDto dto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        userService.update(id, dto);
        return "redirect:/users";
    }
}
