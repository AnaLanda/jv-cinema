package com.cinema.model.dto;

import com.cinema.validation.ValidEmail;
import com.cinema.validation.ValidPassword;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidPassword
public class UserRequestDto {
    @ValidEmail
    private String email;
    private String password;
    @NotNull(message = "The repeat password can't be null.")
    private String repeatPassword;
}
