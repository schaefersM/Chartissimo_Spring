package com.schaefersm.auth.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(String property) {
        super(property + " can not be empty!");
    }

}
