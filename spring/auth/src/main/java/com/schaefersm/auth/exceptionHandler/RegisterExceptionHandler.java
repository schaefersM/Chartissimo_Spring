package com.schaefersm.auth.exceptionHandler;

import com.schaefersm.auth.exception.EmailAlreadyExistsException;
import com.schaefersm.auth.exception.UsernameAlreadyExistsException;
import com.schaefersm.auth.model.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RegisterExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<BaseResponse> handleJwtTokenMalformedException(EmailAlreadyExistsException e) {
        return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<BaseResponse> handleJwtTokenMissingException(UsernameAlreadyExistsException e) {
        return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
