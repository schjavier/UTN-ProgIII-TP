package com.utn.ProgIII.model.ProductSupplier;

public record DatosProductSupplierDTO(Long idProductSupplier,
                                      Long idProduct,
                                      Long idSupplier,
                                      Float cost,
                                      Float profitMargin,
                                      Float price) {
}
