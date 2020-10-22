package com.cinema.model.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private Long id;
    private String email;
    private String password;
}
