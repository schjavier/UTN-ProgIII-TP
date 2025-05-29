package com.utn.ProgIII.model.ProductSupplier.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateProductSupplierDTO(

        BigDecimal cost,

        BigDecimal profitMargin) {
}
