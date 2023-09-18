package com.XAUS.Exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {

    public HttpStatus httpStatus;

    public NotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
