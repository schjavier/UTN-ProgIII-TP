package com.utn.ProgIII.model.Supplier;

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

    @NotBlank(message = "El nombre de la compañía no puede estar vacío")
    @Length(min = 3, max = 75, message = "El nombre de la compañía debe contener entre 3 y 75 caracteres")
    private String companyName;

    @NotBlank(message = "El CUIT de la compañía no puede estar vacío")
    @Pattern(regexp = "^(20|23|24|27|30|33|34)-?\\d{8}-?\\d$", message = "El formato del CUIT no es válido")
    private String cuit;


    @NotBlank(message = "El número de teléfono no puede estar vacío")
    @Pattern(regexp = "^(?:(?:00)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})?\\d{8}$",
            message = "El formato del numero de teléfono no es válido")
    private String phoneNumber;

    @Email(message = "El email no es válido")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address")
    @NotNull(message = "El objeto de dirección está vacío")
    private Address address;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.REMOVE, orphanRemoval = true)
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
