package com.schaefersm.chartissimo.exceptionHandler;

import com.schaefersm.chartissimo.exception.ChartNameTakenException;
import com.schaefersm.chartissimo.exception.UserChartNotFoundException;
import com.schaefersm.chartissimo.exception.UserConfigNotFoundException;
import com.schaefersm.chartissimo.model.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice

public class UserChartsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserConfigNotFoundException.class)
    public ResponseEntity<?> handleUserChartsNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ChartNameTakenException.class)
    public ResponseEntity<BaseResponse> handleChartNameAlreadyTaken(ChartNameTakenException e) {
        return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserChartNotFoundException.class)
    public ResponseEntity<BaseResponse> handleUserChartNotFound(UserChartNotFoundException e) {
        return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

}
