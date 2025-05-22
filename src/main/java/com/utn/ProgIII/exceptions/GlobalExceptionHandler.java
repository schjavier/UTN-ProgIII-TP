package com.utn.ProgIII.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Set;

/**
 * Clase para gestionar de manera global las excepciones.
 * <p>
 * Cada vez que realizamos una validación que lanza una exception debemos agregarla aca.
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getViolatedConstraints(ex));
    }

    @ExceptionHandler(DuplicateUserException.class)
        public ResponseEntity<String> handleDuplicateUserException(DuplicateUserException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT).
                body(ex.getMessage());
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

    private String getViolatedConstraints(ConstraintViolationException ex) {
        List<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations().stream().toList();
        StringBuilder constraintViolationMessages = new StringBuilder("Restricciones violadas: \n");
        for (ConstraintViolation<?> constraintViolation: constraintViolations) {
            String message = constraintViolation.getMessageTemplate();
            constraintViolationMessages.append(message);
            constraintViolationMessages.append("\n");
        }

        return constraintViolationMessages.toString();
    }

}
