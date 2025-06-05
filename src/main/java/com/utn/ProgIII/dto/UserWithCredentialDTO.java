package com.utn.ProgIII.dto;

import com.utn.ProgIII.model.Credential.Credential;
import com.utn.ProgIII.model.User.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Formato que adquieren los datos recibidos desde el mapper previo a ser devueltos como respuesta a una request
 */
public record UserWithCredentialDTO(
        @Schema(example = "1")
        Long idUser,
        @Schema(example = "Carlitos")
        String firstname,
        @Schema(example = "Testeador")
        String lastname,
        @Schema(example = "12345678")
        String dni,
        @Schema(implementation = UserStatus.class)
        String status,
        Credential credential /*Necesita ser un DTO, esto es la implementacion de la clase*/
) {
}
