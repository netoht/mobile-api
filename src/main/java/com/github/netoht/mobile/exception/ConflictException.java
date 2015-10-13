package com.github.netoht.mobile.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(CONFLICT)
public class ConflictException extends ApiException {

    private static final long serialVersionUID = 1L;

    public ConflictException(String msg) {
        super(msg);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}