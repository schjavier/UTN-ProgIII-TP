package com.utn.ProgIII.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;

    @NotBlank(message = "El nombre no debe estar vacio")
    @Size(min = 3, message = "El nombre debe tener al menos 3 letras")
    private String firstname;

    @NotBlank(message = "El apellido no debe estar vacio")
    @Size(min = 3, message = "El apellido debe tener al menos 3 letras")
    private String lastname;

    @NotBlank(message = "El DNI no debe estar vacio")
    @Size(min = 7, max = 8, message = "El DNI debe tener entre 7 y 8 digitos")
    private String dni;

    private UserStatus status = UserStatus.ENABLED;
}
