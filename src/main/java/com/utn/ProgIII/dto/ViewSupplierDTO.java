package com.utn.ProgIII.dto;

import com.utn.ProgIII.model.Address.Address;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public record ViewSupplierDTO(
        Long id,
        String companyName,
        String cuit,
        String phoneNumber,
        String email,
        ViewAddressDTO address){}
