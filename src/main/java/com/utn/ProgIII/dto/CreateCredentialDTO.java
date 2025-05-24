package com.utn.ProgIII.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCredentialDTO(
        @NotBlank
        String username,

        @NotBlank
        String password,

        @NotBlank
        String role
) {
}
