package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.GetUserWithCredentialsDTO;
import com.utn.ProgIII.model.Credentials.Credentials;
import com.utn.ProgIII.model.User.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public GetUserWithCredentialsDTO toUserWithCredentialsDTO(User user, Credentials credentials) {
        Integer iduser = user.getIduser();
        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        String dni = user.getDni();
        String status = user.getStatus().toString();
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        String role = credentials.getRole().toString();

        return new GetUserWithCredentialsDTO(iduser,firstname,lastname,dni,status,username,password,role);
    }
}
