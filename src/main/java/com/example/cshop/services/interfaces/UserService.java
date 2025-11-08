package com.example.cshop.services.interfaces;

import com.example.cshop.dtos.user.UserCreateDto;
import com.example.cshop.dtos.user.UserDto;
import com.example.cshop.dtos.user.UserUpdateDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    UserDto findById(Long id);
    UserDto create(UserCreateDto dto);
    UserDto update(Long id, UserUpdateDto dto);
    void delete(Long id);
}
