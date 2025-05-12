package com.utn.ProgIII.exceptions;

public class CredentialsNotFoundException extends RuntimeException {
    public CredentialsNotFoundException(String message) {
        super(message);
    }
}
