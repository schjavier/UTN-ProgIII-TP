package com.utn.ProgIII.validations;

import com.utn.ProgIII.exceptions.DuplicateUserException;
import com.utn.ProgIII.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidations {

    private final UserRepository userRepository;

    public UserValidations(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void validateUserByDni(String dni){
        if(userRepository.existsByDni(dni)){
            throw new DuplicateUserException("El usuario ya se encuentra registrado");
        }

    }


}
