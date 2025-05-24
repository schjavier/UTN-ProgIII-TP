package com.utn.ProgIII.model.ProductSupplier.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateProductSupplierDTO(
        @NotNull(message = "El costo no puede estas vacío")
        @Positive(message = "El costo no puede ser negativo")
        BigDecimal cost,

        @NotNull(message = "El margen de ganancia no puede estar vacio")
        @Positive(message = "El margen de ganancia no puede ser negativo")
        BigDecimal profitMargin) {
}
