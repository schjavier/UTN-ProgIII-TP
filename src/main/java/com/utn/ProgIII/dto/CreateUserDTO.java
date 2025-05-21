package com.utn.ProgIII.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Formato que adquieren los datos recibidos desde una request previo a ser enviados al mapper
 */
public record CreateUserDTO(
        @NotBlank
        String firstname,

        @NotBlank
        String lastname,

        @NotBlank
        String dni,

        @NotBlank
        String status,

        @NotBlank
        CreateCredentialDTO credential) {
}
