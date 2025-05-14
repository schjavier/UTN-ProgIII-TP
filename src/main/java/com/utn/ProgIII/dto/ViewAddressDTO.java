package com.utn.ProgIII.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ViewAddressDTO(
        @NotNull
        int idaddress,
        @NotEmpty
        String street,
        @NotEmpty
        String number,
        @NotEmpty
        String city) {
}
