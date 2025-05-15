package com.utn.ProgIII.dto;

import com.utn.ProgIII.model.Credential.Credential;
import jakarta.validation.constraints.NotBlank;

/**
 * Formato que adquieren los datos recibidos desde el mapper previo a ser devueltos como respuesta a una request
 */
public record UserWithCredentialDTO(Long iduser,

                                    @NotBlank
                                     String firstname,

                                    @NotBlank
                                     String lastname,

                                    @NotBlank
                                     String dni,

                                    @NotBlank
                                     String status,

                                    @NotBlank
                                     Credential credential) {}
