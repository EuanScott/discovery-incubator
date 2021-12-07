package com.example.springplayground.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DownStreamApiException.class)
    public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex, Throwable throwable) {

        CustomErrorResponse error = new CustomErrorResponse();

        error.setTimestamp(LocalDateTime.now());
        error.setDetails(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}