package com.schaefersm.auth.exceptionHandler;

import com.schaefersm.auth.exception.JwtTokenMalformedException;
import com.schaefersm.auth.exception.JwtTokenMissingException;
import com.schaefersm.auth.exception.ValidationException;
import com.schaefersm.auth.model.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<BaseResponse> handleJwtTokenMalformedException(ValidationException e) {
        return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }


}
