package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;
import com.utn.ProgIII.model.Credential.Credential;
import com.utn.ProgIII.model.Credential.Role;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.model.User.UserStatus;
import org.springframework.stereotype.Component;

/**
 * Clase que se encarga de transformar un usuario en un DTO (objeto de transferencia de datos) o viceversa
 */
@Component
public class UserMapper {

    /**
     * Convierte una instancia de User en un DTO para ser mostrado en una respuesta json
     * @param user La instancia de usuario recibida desde el servicio
     * @return Un DTO con los datos del usuario
     */
    public UserWithCredentialDTO toUserWithCredentialDTO(User user) {
        Long iduser = user.getIdUser();
        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        String dni = user.getDni();
        String status = user.getStatus().toString();

        return new UserWithCredentialDTO(iduser,firstname,lastname,dni,status,user.getCredential());
    }

    /**
     * Convierte un DTO en una instancia de User para ser enviada al repositorio. Incluye sus credenciales (Credential)
     * @param dto Los datos recibidos en la request
     * @return Una instancia de User con su Credential correspondiente
     */
    public User toEntity(CreateUserDTO dto) {
        User result = new User();

        result.setFirstname(dto.firstname());
        result.setLastname(dto.lastname());
        result.setDni(dto.dni());
        result.setStatus(dto.status().isBlank() ? UserStatus.ENABLED : UserStatus.valueOf(dto.status()));

        Credential credential = new Credential();
        credential.setUsername(dto.username());
        credential.setPassword(dto.password());
        credential.setRole(dto.role().isBlank() ? Role.EMPLOYEE : Role.valueOf(dto.role()));

        result.setCredential(credential);

        return result;
    }
}
