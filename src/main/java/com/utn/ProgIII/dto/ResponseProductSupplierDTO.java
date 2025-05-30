package com.utn.ProgIII.dto;

import java.math.BigDecimal;

public record ResponseProductSupplierDTO(Long idProductSupplier,
                                         Long idProduct,
                                         String productName,
                                         Long idSupplier,
                                         String supplierName,
                                         BigDecimal cost,
                                         BigDecimal profitMargin,
                                         BigDecimal price) {
}
