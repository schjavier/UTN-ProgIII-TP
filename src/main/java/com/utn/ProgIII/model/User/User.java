package com.utn.ProgIII.model.User;

import com.utn.ProgIII.model.Credential.Credential;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Clase que se encarga de representar los usuarios recibidos desde el repositorio, emulando la estructura de la tabla
 * homonima que se encuentra en la base de datos
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iduser;

    @NotBlank(message = "El nombre no debe estar vacio")
    @Size(min = 3, message = "El nombre debe tener al menos 3 letras")
    private String firstname;

    @NotBlank(message = "El apellido no debe estar vacio")
    @Size(min = 3, message = "El apellido debe tener al menos 3 letras")
    private String lastname;

    @NotBlank(message = "El DNI no debe estar vacio")
    @Size(min = 7, max = 8, message = "El DNI debe tener entre 7 y 8 digitos")
    private String dni;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idcredential")
    private Credential credential;
}
