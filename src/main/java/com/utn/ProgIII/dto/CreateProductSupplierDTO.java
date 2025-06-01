package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record CreateProductSupplierDTO(
        @Schema(example = "1")
        Long idProduct,
        @Schema(example = "1")
        Long idSupplier,
        @Schema(example = "250")
        BigDecimal cost,
        @Schema(examples = {"20", "10"}, description = "Margen de ganancia seleccionado (usar en porcentajes)")
        BigDecimal profitMargin) {
}
