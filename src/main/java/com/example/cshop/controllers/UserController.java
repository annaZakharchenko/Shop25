package com.example.cshop.controllers;

import com.example.cshop.dtos.order.OrderDto;
import com.example.cshop.dtos.user.UserCreateDto;
import com.example.cshop.dtos.user.UserDto;
import com.example.cshop.dtos.user.UserUpdateDto;
import com.example.cshop.services.interfaces.OrderService;
import com.example.cshop.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;

    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new UserCreateDto());
        return "user/create";
    }
    @GetMapping("/profile/{id}")
    public String profilePageById(@PathVariable Long id, Model model) {
        UserDto user = userService.findById(id);
        List<OrderDto> orders = orderService.getOrdersForUserById(id);

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        return "user/my-profile";
    }

    @GetMapping("/profile/email")
    public String profilePageByEmail(@RequestParam String email, Model model) {
        UserDto user = userService.findByEmail(email);
        List<OrderDto> orders = orderService.getOrdersForUserByEmail(email);

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        return "user/my-profile";
    }

    @GetMapping("/profile/")
    public String profilePage(@RequestParam(value = "edit", required = false) Boolean edit,
                              Model model, Authentication authentication) {

        UserDto user = userService.findByEmail(authentication.getName());
        List<OrderDto> orders = orderService.getOrdersForUserByEmail(authentication.getName());

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        model.addAttribute("editMode", edit != null && edit);

        return "user/my-profile";
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

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") @Valid UserUpdateDto dto,
                                BindingResult bindingResult,
                                Authentication auth,
                                Model model) {

        if (bindingResult.hasErrors()) {
            // снова загрузить заказы + юзера
            String email = auth.getName();
            UserDto user = userService.findByEmail(email);
            List<OrderDto> orders = orderService.getOrdersForUserByEmail(email);

            model.addAttribute("user", user);
            model.addAttribute("orders", orders);
            model.addAttribute("editMode", true); // показать форму

            return "user/my-profile";
        }

        // обновляем данные
        UserDto current = userService.findByEmail(auth.getName());
        userService.update(current.getId(), dto);

        return "redirect:/users/profile/";
    }

}
