package com.utn.ProgIII.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record AddSupplierDTO(
        @NotBlank(message = "El nombre de la compania no puede estar vacio")
        @Length(min = 3,max = 75, message = "El largo del nombre de la compania no es valido")
        String companyName,

        @NotBlank(message = "El cuit de la compania no puede estar vacio!")
        @Pattern(regexp = "^(20|23|24|27|30|33|34)-?\\d{8}-?\\d$", message = "El formato del CUIT no es valido") // como un CUIL 23-24220759-9 o 23242207599 o 23/24220759/9
        String cuit,

        @NotBlank(message = "El numbero de telefono no puede estar vacio!")
        @Pattern(regexp = "^(?:(?:00)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})?\\d{8}$", message = "El formato del numero de telefono no es valido")
        String phoneNumber,

        @Email(message = "El email no es valido!!")
        @NotBlank(message = "El email no puede estar vacio!")
        String email,
        @NotEmpty(message = "El objeto de email esta vacio!")
        AddAddressDTO address) {
}
