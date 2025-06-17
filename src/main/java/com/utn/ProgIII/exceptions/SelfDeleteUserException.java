package com.utn.ProgIII.exceptions;

public class SelfDeleteUserException extends RuntimeException {
    public SelfDeleteUserException(String message) {
        super(message);
    }
}
