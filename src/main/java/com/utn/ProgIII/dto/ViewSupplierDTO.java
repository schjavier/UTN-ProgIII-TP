package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ViewSupplierDTO(
        @Schema(example = "5")
        Long id,
        @Schema(example = "Compañía test")
        String companyName,
        @Schema(example = "23-11111111-9")
        String cuit,
        @Schema(example = "1144358129")
        String phoneNumber,
        @Schema(example = "testemail@email.com")
        String email,
        ViewAddressDTO address) {
}
