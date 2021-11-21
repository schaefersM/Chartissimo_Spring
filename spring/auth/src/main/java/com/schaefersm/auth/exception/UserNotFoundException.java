package com.schaefersm.auth.exception;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Construct a DataRetrievalException with the specified detail message and cause.
     *
     * @param msg The detail message.
     */
    public UserNotFoundException() {
        super("User not found!");
    }


}
