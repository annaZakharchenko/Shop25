package com.example.cshop.services.interfaces;

import com.example.cshop.dtos.user.UserLoginDto;
import com.example.cshop.dtos.user.UserRegisterDto;
import com.example.cshop.models.User;

public interface AuthService {
    User register(UserRegisterDto dto);
    User login(UserLoginDto dto);
    void logout();
}
