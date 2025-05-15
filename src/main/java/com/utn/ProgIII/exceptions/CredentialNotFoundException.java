package com.utn.ProgIII.exceptions;

public class CredentialNotFoundException extends RuntimeException {
    public CredentialNotFoundException(String message) {
        super(message);
    }
}
