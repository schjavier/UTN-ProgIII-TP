package com.utn.ProgIII.dto;

import com.utn.ProgIII.model.Credentials.Credentials;
import jakarta.validation.constraints.NotBlank;

public record UserWithCredentialsDTO(Integer iduser,

                                     @NotBlank
                                     String firstname,

                                     @NotBlank
                                     String lastname,

                                     @NotBlank
                                     String dni,

                                     @NotBlank
                                     String status,

                                     @NotBlank
                                     Credentials credentials) {}
