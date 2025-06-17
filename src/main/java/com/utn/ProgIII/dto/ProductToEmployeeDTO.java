package com.utn.ProgIII.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;

@Builder

public record ProductToEmployeeDTO(
        @Schema(example = "1")
        Long idProduct,
        @Schema(example = "Pasas de uva")
        String name,
        @Schema(example = "ElMayorista2")
        String companyName,
        @Schema(example = "5000")
        BigDecimal price) {
}
