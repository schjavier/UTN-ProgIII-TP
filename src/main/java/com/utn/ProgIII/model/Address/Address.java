package com.utn.ProgIII.model.Address;

import com.utn.ProgIII.model.Supplier.Supplier;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idaddress;
    @NotBlank(message = "La calle no puede estar vacia!")
    private String street;
    @NotBlank(message = "El numero de calle no puede estar vacio!")
    private String number;
    @NotBlank
    private String city;

        /*
    @OneToOne
    @JoinColumn(name = "idSupplier", referencedColumnName = "idSupplier")
    private Address address;*/

    public Address(String city, String number, String street) {
        this.city = city;
        this.number = number;
        this.street = street;
    }
}
