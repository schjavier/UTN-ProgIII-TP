package com.utn.ProgIII.model.Supplier;

import com.utn.ProgIII.model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ViewSupplierDTO(
        @NotNull
        Long id,
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
