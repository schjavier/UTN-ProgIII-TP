package com.utn.ProgIII.validations;

import com.utn.ProgIII.exceptions.DuplicateEntityException;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserValidations {

    private final UserRepository userRepository;

    public UserValidations(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public void validateUserByDni(String dni){
        if(userRepository.existsByDni(dni)){
            throw new DuplicateEntityException("El dni ingresado ya se encuentra registrado");
        }

    }

    public void validateModifiedUserByDni(String currentDni, String newDni){
        if(userRepository.existsByDni(newDni) && !currentDni.equals(newDni)){
            throw new DuplicateEntityException("El dni ingresado ya se encuentra registrado");
        }
    }


    public boolean checkifRequestedUserIsTheSame(User user)
    {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;


        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }


        return username.equals(user.getCredential().getUsername());
    }

}
