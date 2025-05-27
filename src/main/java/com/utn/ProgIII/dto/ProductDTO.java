package com.utn.ProgIII.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder

public record ProductDTO (Long idProduct,

                          @NotBlank
                          String name,

                          @NotBlank
                          String status) {


}
