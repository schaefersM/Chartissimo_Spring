package com.schaefersm.auth.exception;

public class PasswordIsWrongException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PasswordIsWrongException() {
        super("Password is wrong!");
    }

}
