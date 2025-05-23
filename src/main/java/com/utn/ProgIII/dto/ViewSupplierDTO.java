package com.utn.ProgIII.dto;

import com.utn.ProgIII.model.Address.Address;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public record ViewSupplierDTO(
        // no funciona el validator
        @NotNull
        Long id,
        String companyName,
        @Pattern(regexp = "\\b(20|23|24|27|30|33|34)(\\D)?[0-9]{8}(\\D)?[0-9]") // como un CUIL 23-24220759-9 o 23242207599 o 23/24220759/9
        @NotBlank(message = "El cuit de la compania no puede estar vacio!")
        String cuit,
        // @Pattern(regexp = "") necesito algun regex para esto...
        @NotBlank(message = "El numbero de telefono no puede estar vacio!")
        String phoneNumber,
        @Email(message = "El email no es valido!!")
        @NotBlank(message = "El email no puede estar vacio!")
        String email,
        @NotEmpty(message = "El objeto de email esta vacio!")
        ViewAddressDTO address){}
