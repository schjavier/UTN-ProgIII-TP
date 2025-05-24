package com.utn.ProgIII.model.ProductSupplier;


import com.utn.ProgIII.model.Product.ProductStatus;

import java.math.BigDecimal;

public record ExtendedProductDTO(Long idProduct,
                                 String name,
                                 BigDecimal cost,
                                 BigDecimal profitMargin,
                                 BigDecimal price,
                                 ProductStatus status) {
}
