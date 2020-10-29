package com.cinema.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements
        ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}";

    @Override
    public boolean isValid(String email,
                           ConstraintValidatorContext context) {
        return email != null
                && email.matches(EMAIL_REGEX);
    }

}
