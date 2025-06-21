package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequestDTO(
        @Schema(example = "admin")
        String username,
        @Schema(example = "admin1234")
        String password) {


}
