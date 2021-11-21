package com.schaefersm.auth.exception;

public class PasswordIsWrongException extends RuntimeException {

    public PasswordIsWrongException() {
        super("Password is wrong!");
    }

}
