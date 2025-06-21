package com.utn.ProgIII.model.User;

import com.utn.ProgIII.model.Credential.Credential;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.envers.Audited;

/**
 * Clase que se encarga de representar los usuarios recibidos desde el repositorio, emulando la estructura de la tabla
 * homónima que se encuentra en la base de datos
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Audited
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Pattern(regexp = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+( [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$",
            message = "El nombre debe contener letras y las iniciales deben estar en mayúscula")
    @NotBlank(message = "El nombre no debe estar vacío")
    @Size(min = 3, max = 50, message = "El nombre debe contener entre 3 y 50 letras")
    private String firstname;

    @Pattern(regexp = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+( [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$",
            message = "El apellido debe contener letras y las iniciales deben estar en mayúscula")
    @NotBlank(message = "El apellido no debe estar vacío")
    @Size(min = 3, max = 50, message = "El apellido debe contener entre 3 y 50 letras")
    private String lastname;

    @Pattern(regexp = "^\\d+$", message = "El dni solo puede tener números")
    @NotBlank(message = "El DNI no debe estar vacío")
    @Size(min = 7, max = 8, message = "El dni solo puede tener entre 7 y 8 caracteres")
    private String dni;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @NotNull(message = "El usuario debe tener credenciales")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_credential")
    private Credential credential;
}
