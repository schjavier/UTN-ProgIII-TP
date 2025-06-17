package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.Credential.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Clase que se encarga de interactuar con el ORM y el servicio de credenciales
 */
public interface CredentialRepository extends JpaRepository<Credential,Long> {
    boolean existsByUsername(String username);
    Optional<Credential> findByUsername(String username);
}
