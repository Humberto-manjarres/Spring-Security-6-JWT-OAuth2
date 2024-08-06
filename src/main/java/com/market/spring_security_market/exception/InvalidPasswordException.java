package com.market.spring_security_market.exception;

public class InvalidPasswordException extends RuntimeException{

    public InvalidPasswordException() {
        super();
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
