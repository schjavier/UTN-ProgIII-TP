package com.utn.ProgIII.dto;

import jakarta.validation.constraints.NotBlank;

public record AddAddressDTO(
        String street,
        String number,
        String city){}
