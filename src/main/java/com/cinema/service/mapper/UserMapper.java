package com.cinema.service.mapper;

import com.cinema.model.User;
import com.cinema.model.dto.UserRequestDto;

public class UserMapper {
    public User mapToUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        return user;
    }
}
