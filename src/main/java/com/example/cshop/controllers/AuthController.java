package com.example.cshop.controllers;

import com.example.cshop.dtos.user.UserLoginDto;
import com.example.cshop.dtos.user.UserRegisterDto;
import com.example.cshop.services.interfaces.AuthService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // =================== Register ===================
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserRegisterDto());
        return "auth/register"; // Thymeleaf шаблон
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") @Valid UserRegisterDto dto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            authService.register(dto);
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "auth/register";
        }

        return "redirect:/auth/login";
    }

    // =================== Login ===================
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserLoginDto());
        return "auth/login"; // Thymeleaf шаблон
    }

    @PostMapping("/login")
    public String loginUser(
            @ModelAttribute("user") @Valid UserLoginDto dto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }

        try {
            authService.login(dto);
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "auth/login";
        }

        return "redirect:/"; // главная страница после успешного входа
    }

    // =================== Logout ===================
    @PostMapping("/logout")
    public String logoutUser() {
        authService.logout(); // очищаем сессию
        return "redirect:/auth/login?logout";
    }
}
