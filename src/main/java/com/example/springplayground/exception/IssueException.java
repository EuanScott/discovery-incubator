package com.example.springplayground.exception;

public class IssueException extends RuntimeException {

    public IssueException(String msg, Throwable error) {
        super(msg + error);
    }
}