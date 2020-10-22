package com.cinema.service.mapper;

import com.cinema.model.User;
import com.cinema.model.dto.UserRequestDto;
import com.cinema.model.dto.UserResponseDto;

public class UserMapper {
    public UserResponseDto mapToDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }

    public User mapToUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        return user;
    }
}
