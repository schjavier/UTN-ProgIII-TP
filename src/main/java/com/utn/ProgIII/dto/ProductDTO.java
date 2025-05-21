package com.utn.ProgIII.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductDTO (Long idProduct,

                          @NotBlank
                          String name,

                          @NotBlank
                          BigDecimal cost,

                          @NotBlank
                          BigDecimal profitMargin,

                          @NotBlank
                          BigDecimal price) {


}
