package com.utn.ProgIII.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateCredentialDTO(@NotBlank String username,

                                  @NotBlank String password,

                                  @NotBlank String role) {
        public CreateCredentialDTO {
                if (role == null) {
                        role = "EMPLOYEE";
                }
        }

}
