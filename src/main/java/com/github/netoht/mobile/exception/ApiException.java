package com.github.netoht.mobile.exception;

public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Exception e) {
        super(e);
    }
}
