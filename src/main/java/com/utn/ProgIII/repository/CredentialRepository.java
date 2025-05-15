package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.Credential.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential,Long> {
}
