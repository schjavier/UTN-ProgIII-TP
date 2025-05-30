package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record AddSupplierDTO(
        String companyName,
        String cuit,
        String phoneNumber,
        String email,
        AddAddressDTO address) {



}
