package com.utn.ProgIII.model.Address;

import com.utn.ProgIII.model.Supplier.Supplier;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAddress;
    @NotBlank(message = "La calle no puede estar vacia!")
    @Length(min = 3, max = 50, message = "El largo de la calle no es correcto")
    private String street;
    @NotBlank(message = "El numero de calle no puede estar vacio!")
    @Length(min = 2, max = 5, message = "El largo de la calle no es correcto")
    @Pattern(regexp = "\\d+", message = "El numero no tiene solo numeros!")
    private String number;
    @NotBlank(message = "El nombre de la ciudad no puede ser vacio!")
    @Length(message = "El largo no es correcto",min = 3, max = 20)
    private String city;

    public Address(String city, String number, String street) {
        this.city = city;
        this.number = number;
        this.street = street;
    }
}
