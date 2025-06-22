package com.utn.ProgIII.dto;

import com.utn.ProgIII.model.Credential.Role;
import io.swagger.v3.oas.annotations.media.Schema;

public record ViewCredentialsDTO(
        @Schema(example = "Usuario123")
        String username,
        @Schema(example = "(contraseña encriptada)")
        String password,
        @Schema(implementation = Role.class)
        String role
        ) {}
