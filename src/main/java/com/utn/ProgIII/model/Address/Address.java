package com.utn.ProgIII.model.Address;

import com.utn.ProgIII.model.Supplier.Supplier;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAddress;
    @NotBlank
    private String street;
    @NotBlank
    private String number;
    @NotBlank
    private String city;

        /*
    @OneToOne
    @JoinColumn(name = "idSupplier", referencedColumnName = "idSupplier")
    private Address address;*/


}
