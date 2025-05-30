package com.utn.ProgIII.dto;

import java.math.BigDecimal;

public record UpdateProductSupplierDTO(

        BigDecimal cost,

        BigDecimal profitMargin) {
}
