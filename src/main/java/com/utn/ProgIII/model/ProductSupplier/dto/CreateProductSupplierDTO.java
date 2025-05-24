package com.utn.ProgIII.model.ProductSupplier.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateProductSupplierDTO(

        @NotNull(message = "El id del producto no puede estar vacío")
        Long idProduct,

        @NotNull(message = "El id del proveedor no puede estar vacío")
        Long idSupplier,

        @NotNull(message = "El costo no puede estas vacío")
        @Positive(message = "El costo no puede ser negativo")
        BigDecimal cost,

        @Positive(message = "El margen de ganancia no puede ser negativo")
        BigDecimal profitMargin

) {}
