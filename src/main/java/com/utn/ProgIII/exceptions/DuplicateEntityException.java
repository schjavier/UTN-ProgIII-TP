package com.utn.ProgIII.exceptions;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String msg) {
        super(msg);
    }
}
