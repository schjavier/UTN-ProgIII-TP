package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateProductSupplierDTO(
        @Schema(example = "300")
        @NotNull(message = "El costo no puede ser nulo")
        @PositiveOrZero(message = "El costo no puede ser menos de 0")
        BigDecimal cost,
        @Schema(example = "30")
        @NotNull(message = "El margen de ganancias no puede ser nulo")
        @PositiveOrZero(message = "El margen de ganancias no puede ser menos de 0")
        BigDecimal profitMargin) {
}
