package com.utn.ProgIII.exceptions;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String msg) {
        super(msg);
    }
}
