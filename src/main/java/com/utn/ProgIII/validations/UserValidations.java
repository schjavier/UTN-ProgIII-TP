package com.utn.ProgIII.validations;

import com.utn.ProgIII.exceptions.DuplicateUserException;
import com.utn.ProgIII.exceptions.InvalidCharactersException;
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
            throw new DuplicateUserException("El dni ingresado ya se encuentra registrado");
        }

    }

    public void validateModifiedUserByDni(String currentDni, String newDni){
        if(userRepository.existsByDni(newDni) && !currentDni.equals(newDni)){
            throw new DuplicateUserException("El dni ingresado ya se encuentra registrado");
        }

    }

    public void validateNameCharacters(String name) {
        if (!name.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            throw new InvalidCharactersException("El nombre solo puede tener letras y espacios");
        }
    }


}
