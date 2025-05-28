package com.utn.ProgIII.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record AddAddressDTO(
        // no funciona el validator
        @NotBlank(message = "La calle no puede estar vacia!")
        @Length(min = 3, max = 50, message = "El largo de la calle no es correcto")
        String street,
        @NotBlank(message = "La altura de la calle no puede estar vacia")
        @Length(min = 2, max = 5, message = "La altura de la calle debe contener entre 1 y 5 numeros ")
        @Pattern(regexp = "\\d+", message = "La altura de la calle tiene caracteres no numericos")
        String number,
        @NotBlank(message = "El nombre de la ciudad no puede ser vacio!")
        @Length(message = "El largo no es correcto",min = 3, max = 20)
        String city){}
