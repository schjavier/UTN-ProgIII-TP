package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record UpdateProductSupplierDTO(
        @Schema(example = "300")
        BigDecimal cost,
        @Schema(example = "30")
        BigDecimal profitMargin) {
}
