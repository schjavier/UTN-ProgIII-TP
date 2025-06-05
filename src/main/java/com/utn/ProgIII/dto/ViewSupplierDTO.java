package com.utn.ProgIII.dto;

public record ViewSupplierDTO(
        Long id,
        String companyName,
        String cuit,
        String phoneNumber,
        String email,
        ViewAddressDTO address) {
}
