package com.utn.ProgIII.validations;

import com.utn.ProgIII.exceptions.DuplicateEntityException;
import com.utn.ProgIII.repository.CredentialRepository;
import org.springframework.stereotype.Component;

@Component
/*
 * Componente que se encarga de las validaciones relacionadas a credenciales
 */
public class CredentialValidations {
    private final CredentialRepository credentialRepository;

    public CredentialValidations(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    /**
     * Valida si el nombre de usuario existe, se lanza una excepción si es el caso
     * @param username Nombre de usuario
     */
    public void validateUsernameNotExists(String username) {
        if(credentialRepository.existsByUsername(username)) {
            throw new DuplicateEntityException("El nombre de usuario ya existe en la base de datos");
        }
    }

    /**
     * Valida que no se repitan los nombres de usuario en la base de datos cuando se modifica alguno
     * @param currentUsername Nombre de usuario actual
     * @param newUsername Nombre de usuario nuevo
     */
    public void validateModifiedUsernameNotExists(String currentUsername, String newUsername) {
        if(credentialRepository.existsByUsername(newUsername) && !newUsername.equals(currentUsername)) {
            throw new DuplicateEntityException("El nombre de usuario ingresado ya existe en la base de datos");
        }
    }

}
