package com.utn.ProgIII.dto;

import com.utn.ProgIII.model.Credential.Role;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CreateCredentialDTO(
        @Schema(example = "Username_test")
        String username,
        @Schema(example = "password123")
        String password,
        @Schema(implementation = Role.class)
        String role) {
        public CreateCredentialDTO {
                if (role == null) {
                        role = "EMPLOYEE";
                }
        }

}
