package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponseDTO(
        @Schema(example = "(jwt)")
        String token
) {
}
