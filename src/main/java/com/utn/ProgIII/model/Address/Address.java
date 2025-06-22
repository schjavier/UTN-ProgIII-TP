package com.utn.ProgIII.model.Address;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Audited
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAddress;

    @NotBlank(message = "El nombre de la calle no puede estar vacío")
    @Length(min = 3, max = 50, message = "El nombre de la calle debe contener entre 3 y 50 letras")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$",
            message = "El nombre de la calle solo puede contener letras, espacios, y números")
    private String street;

    @NotBlank(message = "La altura de la calle no puede estar vacía")
    @Length(min = 2, max = 5, message = "La altura de la calle debe contener entre 2 y 5 números ")
    @Pattern(regexp = "\\d+", message = "La altura de la calle tiene caracteres no numéricos")
    private String number;

    @NotBlank(message = "El nombre de la ciudad no puede estar vacío")
    @Length(min = 3, max = 50, message = "El nombre de la ciudad debe contener entre 3 y 50 letras")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$",
            message = "El nombre de la ciudad solo puede contener letras y espacios")
    private String city;

    public Address(String city, String number, String street) {
        this.city = city;
        this.number = number;
        this.street = street;
    }
}
