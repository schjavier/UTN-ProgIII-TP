package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ViewAddressDTO(
        @Schema(example = "5")
        Long idaddress,
        @Schema(example = "Calle test")
        String street,
        @Schema(example = "123")
        String number,
        @Schema(example = "City test")
        String city) {
}
