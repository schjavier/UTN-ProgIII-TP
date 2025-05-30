package com.utn.ProgIII.dto;

import lombok.Builder;

@Builder
public record CreateCredentialDTO(String username,
                                  String password,
                                  String role) {
        public CreateCredentialDTO {
                if (role == null) {
                        role = "EMPLOYEE";
                }
        }

}
