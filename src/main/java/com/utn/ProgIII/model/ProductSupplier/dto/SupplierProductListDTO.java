package com.utn.ProgIII.model.ProductSupplier.dto;

import java.util.List;

public record SupplierProductListDTO(Long idSupplier, String companyName, List<ExtendedProductDTO> productsList) {
}
