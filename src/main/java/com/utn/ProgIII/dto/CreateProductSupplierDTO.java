package com.utn.ProgIII.dto;

import java.math.BigDecimal;

public record CreateProductSupplierDTO(

        Long idProduct,

        Long idSupplier,

        BigDecimal cost,

        BigDecimal profitMargin

) {}
