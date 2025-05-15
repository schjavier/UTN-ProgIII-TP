package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;
import com.utn.ProgIII.model.Credential.Credential;
import com.utn.ProgIII.model.Credential.Role;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.model.User.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserWithCredentialDTO toUserWithCredentialDTO(User user) {
        Long iduser = user.getIduser();
        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        String dni = user.getDni();
        String status = user.getStatus().toString();

        return new UserWithCredentialDTO(iduser,firstname,lastname,dni,status,user.getCredential());
    }

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
