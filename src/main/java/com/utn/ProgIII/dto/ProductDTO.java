package com.utn.ProgIII.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductDTO (Long idProduct,

                          @NotBlank
                          String name,

                          @NotBlank
                          String status) {


}
