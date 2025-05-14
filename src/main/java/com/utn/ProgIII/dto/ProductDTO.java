package com.utn.ProgIII.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductDTO (Integer idProduct,

                          @NotBlank
                          String name,

                          @NotBlank
                          double cost,

                          @NotBlank
                          double profitMargin,

                          @NotBlank
                          double price) {}
