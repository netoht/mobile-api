package com.github.netoht.mobile.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(BAD_REQUEST)
public class IllegalFieldsException extends ApiException {

    private static final long serialVersionUID = 1L;

    public IllegalFieldsException(String msg) {
        super(msg);
    }

    public IllegalFieldsException(String message, Throwable cause) {
        super(message, cause);
    }
}
