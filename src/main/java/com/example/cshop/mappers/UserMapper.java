package com.example.cshop.mappers;

import com.example.cshop.dtos.user.UserCreateDto;
import com.example.cshop.dtos.user.UserDto;
import com.example.cshop.dtos.user.UserUpdateDto;
import com.example.cshop.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserCreateDto dto);
    void updateEntity(UserUpdateDto dto, @MappingTarget User user);
}
