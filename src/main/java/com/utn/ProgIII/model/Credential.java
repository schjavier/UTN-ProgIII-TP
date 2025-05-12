package com.utn.ProgIII.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Credential {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int idCredentials;

    @OneToOne(mappedBy = "idUser", cascade = CascadeType.ALL)
    private int idUser;

    @NotBlank(message = "El usuario no debe estar vacio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    private String username;

    @NotBlank(message = "La clave no debe estar vacia")
    @Size(min = 3, max = 20, message = "La contrasenia debe tener entre 3 y 50 caracteres")
    private String password;

    private Role role;
}
