package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;
import com.utn.ProgIII.dto.ViewCredentialsDTO;
import com.utn.ProgIII.exceptions.NullCredentialsException;
import com.utn.ProgIII.model.Credential.Credential;
import com.utn.ProgIII.model.Credential.Role;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.model.User.UserStatus;
import com.utn.ProgIII.exceptions.InvalidRequestException;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Clase que se encarga de transformar un usuario en un DTO (objeto de transferencia de datos) o viceversa
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

    @Autowired
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private final PasswordEncoder passwordEncoder;
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

        return new UserWithCredentialDTO(iduser,firstname,lastname,dni,status,new ViewCredentialsDTO(
                user.getCredential().getUsername(),
                user.getCredential().getPassword(),
                user.getCredential().getRole().toString()
        )
        );
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


        if (dto.credential() == null) {
            throw new NullCredentialsException("El usuario debe tener credenciales");
        }

        if(!EnumUtils.isValidEnum(UserStatus.class, dto.status().toUpperCase()))
        {
            throw new InvalidRequestException("El estado no es valido");
        }

        result.setStatus(UserStatus.valueOf(dto.status().toUpperCase()));

        if(!EnumUtils.isValidEnum(Role.class, dto.credential().role()))
        {
            throw new InvalidRequestException("El rol no es valido");
        }


        Credential credential = Credential.builder()
                .password(dto.credential().password())
                .username(dto.credential().username())
                .role(Role.valueOf(dto.credential().role().toUpperCase()))
                .build();
        result.setCredential(credential);

        Set<ConstraintViolation<Credential>> violations = validator.validate(result.getCredential());
        if(!violations.isEmpty())
        {
            throw new ConstraintViolationException(violations);
        }

        credential.setPassword(passwordEncoder.encode(dto.credential().password()));
        return result;
    }
}
