package com.utn.ProgIII.dto;

import com.utn.ProgIII.model.Credential.Credential;

/**
 * Formato que adquieren los datos recibidos desde el mapper previo a ser devueltos como respuesta a una request
 */
public record UserWithCredentialDTO(Long idUser,
                                     String firstname,
                                     String lastname,
                                     String dni,
                                     String status,
                                     Credential credential) {
}
