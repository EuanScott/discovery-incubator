package com.example.springplayground.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CustomErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    // private int status;
    // private String message;
    // private String error;
    private String details;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // public int getStatus() {
    //     return status;
    // }
    //
    // public void setStatus(int status) {
    //     this.status = status;
    // }

    // public String getMessage() {
    //     return message;
    // }
    //
    // public void setMessage(String message) {
    //     this.message = message;
    // }

    // public String getError() {
    //     return error;
    // }
    //
    // public void setError(String error) {
    //     this.error = error;
    // }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}