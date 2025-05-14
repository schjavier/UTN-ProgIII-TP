package com.utn.ProgIII.dto;

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
                                     String username,

                                     @NotBlank
                                     String password,

                                     @NotBlank
                                     String role) {}
