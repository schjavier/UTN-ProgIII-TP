package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.Credentials.Credentials;
import com.utn.ProgIII.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialsRepository extends JpaRepository<Credentials,Integer> {
    Optional<Credentials> findByProfile(User user);
}
