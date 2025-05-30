package com.utn.ProgIII.model.User;

import com.utn.ProgIII.model.Credential.Credential;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Pattern(regexp = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+( [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$",
            message = "El nombre debe contener letras y las iniciales deben estar en mayusculas")
    @NotBlank(message = "El nombre no debe estar vacio")
    @Size(min = 3, max = 50, message = "El nombre debe contener entre 3 y 50 letras")
    private String firstname;

    @Pattern(regexp = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+( [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$",
            message = "El nombre debe contener letras y las iniciales deben estar en mayusculas")
    @NotBlank(message = "El apellido no debe estar vacio")
    @Size(min = 3, max = 50, message = "El apellido debe contener entre 3 y 50 letras")
    private String lastname;

    @Pattern(regexp = "^\\d+$", message = "El dni solo puede tener numeros")
    @NotBlank(message = "El DNI no debe estar vacio")
    @Size(min = 7, max = 8, message = "El dni solo puede tener entre 7 y 8 caracteres")
    private String dni;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @NotNull(message = "El usuario debe tener credenciales")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_credential")
    private Credential credential;
}
