package com.utn.ProgIII.model.Supplier;

import com.utn.ProgIII.model.Address.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Supplier {
    /*@GeneratedValue(strategy = GenerationType.IDENTITY)*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsupplier")
    private int idsupplier;
    @NotBlank(message = "El nombre de la compania no puede estar vacio")
    private String companyname;
    @NotBlank(message = "El cuit no puede estar vacio")
    private String cuit;
    @NotBlank
    private String phonenumber;
    @NotBlank
    @Email
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idsupplier", referencedColumnName = "idsupplier")
    private Address address;

    public Supplier(String companyname, String cuit, String phonenumber, String email, Address address) {
        this.companyname = companyname;
        this.cuit = cuit;
        this.phonenumber = phonenumber;
        this.email = email;
        this.address = address;
    }
}
