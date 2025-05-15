package com.utn.ProgIII.dto;

import com.utn.ProgIII.model.Credential.Credential;
import jakarta.validation.constraints.NotBlank;

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
