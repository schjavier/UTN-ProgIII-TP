package com.utn.ProgIII.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record ViewAddressDTO(
        Long idaddress,
        String street,
        String number,
        String city) {
}
