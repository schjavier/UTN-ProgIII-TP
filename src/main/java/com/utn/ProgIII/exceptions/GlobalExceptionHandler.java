package com.utn.ProgIII.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * Clase para gestionar de manera global las excepciones.
 * <p>
 * Cada vez que realizamos una validación que lanza una exception debemos agregarla aca.
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({SupplierNotFoundException.class})
    public ResponseEntity<Object> supplierNotFoundException(SupplierNotFoundException ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler({AddressNotFoundException.class})
    public ResponseEntity<Object> addressNotFoundException(AddressNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getViolatedConstraints(ex));
    }

    @ExceptionHandler(DuplicateEntityException.class)
        public ResponseEntity<String> handleDuplicateEntityException(DuplicateEntityException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT).
                body(ex.getMessage());
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    @ExceptionHandler({CredentialNotFoundException.class})
    public ResponseEntity<Object> handleCredentialNotFoundException(CredentialNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler({InvalidRequestException.class})
    public ResponseEntity<Object> invalidRequestException(InvalidRequestException ex)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex);
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

    @ExceptionHandler(DuplicateRelationshipException.class)
    public ResponseEntity<String> handleDuplicateRelationshipException(DuplicateRelationshipException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ProductSupplierNotExistException.class)
    public ResponseEntity<String> handleProductSupplierNotExistException(ProductSupplierNotExistException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
