package com.jeiyuen.ecommerce.exceptions;

//Custom Exception for handling general API exceptions
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }

}
