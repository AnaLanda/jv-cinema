package com.cinema.validation;

import com.cinema.model.dto.UserRequestDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements
        ConstraintValidator<ValidPassword, UserRequestDto> {
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}";

    @Override
    public boolean isValid(UserRequestDto requestDto,
                           ConstraintValidatorContext context) {
        return requestDto.getEmail() != null
                && requestDto.getEmail().matches(EMAIL_REGEX);
    }

}
