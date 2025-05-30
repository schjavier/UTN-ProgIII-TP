package com.utn.ProgIII.dto;

import lombok.Builder;

@Builder
public record AddAddressDTO(
        String street,
        String number,
        String city){}
