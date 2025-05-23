package com.utn.ProgIII.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record ViewAddressDTO(
        // no funciona el validator
        @NotNull
        Long idaddress,
        @NotBlank(message = "La calle no puede estar vacia!")
        @Length(min = 3, max = 50, message = "El largo de la calle no es correcto")
        String street,
        @NotBlank(message = "El numero de calle no puede estar vacio!")
        @Length(min = 2, max = 5, message = "El largo de la calle no es correcto")
        @Pattern(regexp = "\\d+", message = "El numero no tiene solo numeros!")
        String number,
        @NotBlank(message = "El nombre de la ciudad no puede ser vacio!")
        @Length(message = "El largo no es correcto",min = 3, max = 20)
        String city) {
}
