package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record AddAddressDTO(
        @Schema(example = "Calle test")
        String street,
        @Schema(example = "123")
        String number,
        @Schema(example = "City test")
        String city){}
