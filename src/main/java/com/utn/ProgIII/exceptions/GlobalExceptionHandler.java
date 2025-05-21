package com.utn.ProgIII.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Clase para gestionar de manera global las excepciones.
 * <p>
 * Cada vez que realizamos una validación que lanza una exception debemos agregarla aca.
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({SupplierNotFoundException.class})
    public ResponseEntity<Object> supplierNotFoundException(SupplierNotFoundException exception)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception);
    }

    @ExceptionHandler({AddressNotFoundException.class})
    public ResponseEntity<Object> addressNotFoundException(AddressNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
    @ExceptionHandler({CredentialNotFoundException.class})
    public ResponseEntity<Object> handleCredentialNotFoundException(CredentialNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({InvalidRequestException.class})
    public ResponseEntity<Object> invalidRequestException(InvalidRequestException exception)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception);
    }
}
