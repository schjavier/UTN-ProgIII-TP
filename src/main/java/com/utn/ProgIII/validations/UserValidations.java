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

    public void validateDniCharacters(String dni) {
        if(!dni.matches("^\\d{7,8}$")) {
            throw new InvalidCharactersException("El dni solo puede tener numeros y entre 7 y 8 caracteres");
        }
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
        if (!name.matches("^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+( [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$")) {
            throw new InvalidCharactersException("El nombre solo puede tener letras y espacios" +
                    " y las inciales deben estar en mayusculas");
        }
    }


}
