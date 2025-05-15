package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Clase que se encarga de interactuar con el ORM y el servicio de usuarios
 */
public interface UserRepository extends JpaRepository<User,Long> {
}
