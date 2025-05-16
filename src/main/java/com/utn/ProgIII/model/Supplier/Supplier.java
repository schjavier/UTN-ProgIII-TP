package com.utn.ProgIII.model.Supplier;

import com.utn.ProgIII.model.Address.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idsupplier;

    @NotBlank(message = "El nombre de la compania no puede estar vacio")
    @Length(min = 3,max = 75, message = "El largo del nombre de la compania no es valido")
    private String companyName; // row MUST be named company_name at the db otherwise it doesn't work for some reason.

    @Pattern(regexp = "\\b(20|23|24|27|30|33|34)(\\D)?[0-9]{8}(\\D)?[0-9]") // como un CUIL 23-24220759-9 o 23242207599 o 23/24220759/9
    @NotBlank(message = "El cuit de la compania no puede estar vacio!")
    private String cuit;

    @Pattern(regexp = "/^(?:(?:00)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$/", message = "El formato del numero de telefono no es valido!")
    @NotBlank(message = "El numbero de telefono no puede estar vacio!")
    private String phoneNumber; // row MUST be named phone_number at the db otherwise it doesn't work for some reason.

    @Email(message = "El email no es valido!!")
    @NotBlank(message = "El email no puede estar vacio!")
    private String email;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idaddress")
    private Address address;


    public Supplier(String companyname, String cuit, String phonenumber, String email, Address address) {
        this.companyName = companyname;
        this.cuit = cuit;
        this.phoneNumber = phonenumber;
        this.email = email;
        this.address = address;
    }
}
