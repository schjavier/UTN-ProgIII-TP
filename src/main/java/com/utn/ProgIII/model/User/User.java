package com.utn.ProgIII.model.User;

import com.utn.ProgIII.model.Credential.Credential;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    private Long idUser;

    @Pattern(regexp = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+( [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$",
            message = "El nombre debe contener letras y las iniciales deben estar en mayusculas")
    @NotBlank(message = "El nombre no debe estar vacio")
    @Size(min = 3, message = "El nombre debe tener al menos 3 letras")
    private String firstname;

    @Pattern(regexp = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+( [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$",
            message = "El nombre debe contener letras y las iniciales deben estar en mayusculas")
    @NotBlank(message = "El apellido no debe estar vacio")
    @Size(min = 3, message = "El apellido debe tener al menos 3 letras")
    private String lastname;

    @Pattern(regexp = "^\\d{7,8}$", message = "El dni solo puede tener numeros y entre 7 y 8 caracteres")
    @NotBlank(message = "El DNI no debe estar vacio")
    private String dni;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_credential")
    private Credential credential;
}
