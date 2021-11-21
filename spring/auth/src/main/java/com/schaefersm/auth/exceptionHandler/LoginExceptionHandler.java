package com.schaefersm.auth.exceptionHandler;

import com.schaefersm.auth.exception.EmailAlreadyExistsException;
import com.schaefersm.auth.exception.PasswordIsWrongException;
import com.schaefersm.auth.exception.UserNotFoundException;
import com.schaefersm.auth.model.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class LoginExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PasswordIsWrongException.class)
    public ResponseEntity<BaseResponse> handlePasswordIsWrongException(PasswordIsWrongException e) {
        return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseResponse> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<BaseResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

}
