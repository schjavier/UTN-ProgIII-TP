package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record AddSupplierDTO(
        @Schema(example = "Empresa test")
        String companyName,
        @Schema(example = "23-11111111-9")
        String cuit,
        @Schema(example = "1144358129")
        String phoneNumber,
        @Schema(example = "testemail@email.com")
        String email,
        AddAddressDTO address) {



}
