package com.utn.ProgIII.model.User;

import com.utn.ProgIII.model.Credentials.Credentials;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iduser;

    @NotBlank(message = "El nombre no debe estar vacio")
    @Size(min = 3, message = "El nombre debe tener al menos 3 letras")
    private String name;

    @NotBlank(message = "El apellido no debe estar vacio")
    @Size(min = 3, message = "El apellido debe tener al menos 3 letras")
    private String lastname;

    @NotBlank(message = "El DNI no debe estar vacio")
    @Size(min = 7, max = 8, message = "El DNI debe tener entre 7 y 8 digitos")
    private String dni;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToOne
    @JoinColumn(name = "iduser",referencedColumnName = "iduser")
    private Credentials credentials;

    public User(String name, String lastname, String dni, UserStatus status) {
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.status = status;
    }
}
