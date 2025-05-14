package com.utn.ProgIII.dto;

import com.utn.ProgIII.model.Address.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ViewSupplierDTO(
        @NotNull
        int id,
        @NotBlank
        String companyName,
        @NotBlank
        String cuit,
        @NotBlank
        String phoneNumber,
        @Email
        String email,
        @NotEmpty
        Address address){}
