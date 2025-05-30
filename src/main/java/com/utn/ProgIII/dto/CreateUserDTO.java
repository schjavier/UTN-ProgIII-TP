package com.utn.ProgIII.dto;

import lombok.Builder;

/**
 * Formato que adquieren los datos recibidos desde una request previo a ser enviados al mapper
 */
@Builder
public record CreateUserDTO(String firstname,
                            String lastname,
                            String dni,
                            String status,
                            CreateCredentialDTO credential) {
        public CreateUserDTO {
                if (status == null) {
                        status = "ENABLED";
                }
        }
}
