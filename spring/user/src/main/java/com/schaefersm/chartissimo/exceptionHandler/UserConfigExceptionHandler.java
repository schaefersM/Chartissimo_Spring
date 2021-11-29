package com.schaefersm.chartissimo.exceptionHandler;

import com.schaefersm.chartissimo.exception.UserConfigNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice

public class UserConfigExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserConfigNotFoundException.class)
    public ResponseEntity<?> handleUserConfigNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
