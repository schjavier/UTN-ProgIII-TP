package com.utn.ProgIII.dto;

import lombok.Builder;

@Builder
public record AddSupplierDTO(
        String companyName,
        String cuit,
        String phoneNumber,
        String email,
        AddAddressDTO address) {



}
