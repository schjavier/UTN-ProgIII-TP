package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SupplierProductListToEmployeeDTO(
        @Schema(example = "1")
        Long idSupplier,
        @Schema(example = "Compania test")
        String companyName,
        List<ExtendedProductToEmployeeDTO> productsToEmployeeList
) {
}
