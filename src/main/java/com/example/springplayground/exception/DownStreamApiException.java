package com.example.springplayground.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class DownStreamApiException extends RuntimeException {

    public DownStreamApiException(Throwable error) {
        super("An error occurred Downstream: " + error.getMessage());
    }
}