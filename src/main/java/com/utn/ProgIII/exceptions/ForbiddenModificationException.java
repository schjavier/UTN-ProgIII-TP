package com.utn.ProgIII.exceptions;

public class ForbiddenModificationException extends RuntimeException {
    public ForbiddenModificationException(String message) {
        super(message);
    }
}
