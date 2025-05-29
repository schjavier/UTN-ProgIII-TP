package com.utn.ProgIII.model.ProductSupplier.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateProductSupplierDTO(

        Long idProduct,

        Long idSupplier,

        BigDecimal cost,

        BigDecimal profitMargin

) {}
