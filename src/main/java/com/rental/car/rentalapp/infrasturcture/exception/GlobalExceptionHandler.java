package com.rental.car.rentalapp.infrasturcture.exception;

import com.rental.car.rentalapp.infrasturcture.dto.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value=AppException.class)
    @ResponseBody
    public ResponseEntity<Object> appExceptionHandler(AppException e){
        return new ResponseEntity<>(e.resultCode, e.errorMsg, null);
    }

    @ExceptionHandler(value=Exception.class)
    @ResponseBody
    public ResponseEntity<Object> exceptionHandler(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(ResultCode.INTERNAL_SERVER_ERROR);
    }
}
