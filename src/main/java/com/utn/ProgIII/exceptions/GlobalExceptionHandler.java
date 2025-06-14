package com.utn.ProgIII.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler({NullAddressException.class})
    public ResponseEntity<Object> nullAddressException(NullAddressException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler({NullCredentialsException.class})
    public ResponseEntity<Object> nullCredentialsException(NullCredentialsException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    private String getViolatedConstraints(ConstraintViolationException ex) {
        List<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations().stream().toList();
        StringBuilder constraintViolationMessages = new StringBuilder("Restricciones vulneradas: \n");
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

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> productNotFoundException(ProductNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidProductStatusException.class)
    public ResponseEntity<String> invalidProductStatusException (InvalidProductStatusException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                         .body(ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El usuario y la contraseña no coinciden");
    }

    @ExceptionHandler(UnexpectedServerErrorException.class)
    public ResponseEntity<String> UnexpectedErrorException(UnexpectedServerErrorException e) {
        if(e.getHttpcode() != -1)
        {
            return ResponseEntity.status(e.getHttpcode()).body(e.getMessage()); // esto es muy improvable que pase... pero puede salvarnos...
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> ExpiredTokenException(ExpiredJwtException e)
    {
        return ResponseEntity.status(419).body("La sesión ha expirado. Por favor, iniciar sesion de nuevo");
    }

    @ExceptionHandler(SelfDeleteUserException.class)
    public ResponseEntity<String> SelfDeleteUserProtection(SelfDeleteUserException e)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<String> wrongFieldSortException(PropertyReferenceException e)
    {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
