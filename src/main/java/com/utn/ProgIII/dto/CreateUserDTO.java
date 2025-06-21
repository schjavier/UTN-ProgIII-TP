package com.utn.ProgIII.dto;

import com.utn.ProgIII.model.User.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * Formato que adquieren los datos recibidos desde una request previo a ser enviados al mapper
 */
@Builder
public record CreateUserDTO(
        @Schema(example = "Carlitos")
        String firstname,
        @Schema(example = "Testeador")
        String lastname,
        @Schema(example = "12345678")
        String dni,
        @Schema(implementation = UserStatus.class)
        String status,
        CreateCredentialDTO credential) {
        public CreateUserDTO {
                if (status == null) {
                        status = "ENABLED";
                }
        }
}
