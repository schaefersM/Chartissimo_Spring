package com.schaefersm.chartissimo.exception;

public class UserConfigNotFoundException extends RuntimeException {

    public UserConfigNotFoundException() {
        super("User config not found in database!");
    }

}
