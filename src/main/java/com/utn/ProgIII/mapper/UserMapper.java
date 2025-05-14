package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialsDTO;
import com.utn.ProgIII.model.Credentials.Credentials;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.model.User.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserWithCredentialsDTO toUserWithCredentialsDTO(User user, Credentials credentials) {
        Integer iduser = user.getIduser();
        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        String dni = user.getDni();
        String status = user.getStatus().toString();
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        String role = credentials.getRole().toString();

        return new UserWithCredentialsDTO(iduser,firstname,lastname,dni,status,username,password,role);
    }

    public User toEntity(CreateUserDTO dto) {
        User result = new User();

        result.setFirstname(dto.firstname());
        result.setLastname(dto.lastname());
        result.setDni(dto.dni());
        result.setStatus(dto.status().isBlank() ? UserStatus.ENABLED : UserStatus.valueOf(dto.status()));

        return result;
    }
}
