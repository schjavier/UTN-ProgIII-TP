package com.utn.ProgIII.exceptions;

public class InvalidProductStatusException extends RuntimeException {

    public InvalidProductStatusException(String message) {
        super(message);
    }
}
