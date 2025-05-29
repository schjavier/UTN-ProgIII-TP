package com.utn.ProgIII.model.Supplier;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.utn.ProgIII.model.Address.Address;
import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSupplier;

    @NotBlank(message = "El nombre de la compania no puede estar vacio")
    @Length(min = 3, max = 75, message = "El nombre de la compania debe contener entre 3 y 75 caracteres")
    private String companyName;

    @NotBlank(message = "El cuit de la compania no puede estar vacio")
    @Pattern(regexp = "^(20|23|24|27|30|33|34)-?\\d{8}-?\\d$", message = "El formato del CUIT no es valido") // como un CUIL 23-24220759-9 o 23242207599 o 23/24220759/9
    private String cuit;


    @NotBlank(message = "El numero de telefono no puede estar vacio")
    @Pattern(regexp = "^(?:(?:00)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})?\\d{8}$",
            message = "El formato del numero de telefono no es valido")
    private String phoneNumber;

    @Email(message = "El email no es valido")
    @NotBlank(message = "El email no puede estar vacio")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address")
    @NotNull(message = "El objeto de direccion esta vacio!")
    private Address address;

    @OneToMany(mappedBy = "idProductSupplier")
    @ToString.Exclude
    private List<ProductSupplier> productSupplier;

    public Supplier(String companyname, String cuit, String phonenumber, String email, Address address) {
        this.companyName = companyname;
        this.cuit = cuit;
        this.phoneNumber = phonenumber;
        this.email = email;
        this.address = address;
    }
}
