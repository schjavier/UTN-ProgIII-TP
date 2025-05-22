package com.utn.ProgIII.model.Credential;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que se encarga de representar las credenciales recibidas desde el repositorio, emulando la estructura de la tabla
 * homonima que se encuentra en la base de datos
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCredential;

    @NotBlank(message = "El usuario no debe estar vacio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$",
            message = "El nombre de usuario solo puede tener letras, numeros, y guiones bajos")
    private String username;

    @NotBlank(message = "La clave no debe estar vacia")
    @Size(min = 3, max = 20, message = "La contrasenia debe tener entre 3 y 50 caracteres")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
