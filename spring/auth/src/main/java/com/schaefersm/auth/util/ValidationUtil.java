package com.schaefersm.auth.util;

import com.schaefersm.auth.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    public void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new ValidationException("Username");
        }
    }

    public void validatePassword(String password) {
        if (password ==  null || password.isBlank()) {
            throw new ValidationException("Password");
        }
    }

    public void validateEmail(String email) {
        if (email ==  null || email.isBlank()) {
            throw new ValidationException("Email");
        }
    }
}
