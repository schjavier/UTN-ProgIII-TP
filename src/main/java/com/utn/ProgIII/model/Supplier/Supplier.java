package com.utn.ProgIII.model.Supplier;

import com.utn.ProgIII.model.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSupplier;
    @NotBlank
    private String companyName;
    @NotBlank
    private String cuit;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String email;

    @OneToOne
    @JoinColumn(name = "idSupplier")
    private Address address;

}
