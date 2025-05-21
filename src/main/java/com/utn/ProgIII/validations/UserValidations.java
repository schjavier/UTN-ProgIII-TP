package com.utn.ProgIII.validations;

import com.utn.ProgIII.exceptions.DuplicateUserException;
import com.utn.ProgIII.exceptions.UserNotFoundException;
import com.utn.ProgIII.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidations {

    private final UserRepository userRepository;

    public UserValidations(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    /**
     * Verifica que exista el usuario en base al DNI.
     *
     * @param dni
     */
    public void validateUserByDni(String dni){
        if(userRepository.existsByDni(dni)){
            throw new DuplicateUserException("El usuario ya se encuentra registrado");
        }

    }

    /**
     * checkea la existencia del usuario, lanza una excepción si no existe.
     *
     * @param id -> el id del usuario a buscar
     *
     */

    public void userExist(Long id){

        if (!userRepository.existsById(id)){
            throw new UserNotFoundException("El usuario no existe");
        }


    }


}
