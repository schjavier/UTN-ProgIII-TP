package com.utn.ProgIII.model.Credential;

import lombok.AllArgsConstructor;

/**
 * Los tipos de usuario existentes en el sistema
 */
@AllArgsConstructor
public enum Role {
    ADMIN("admin"),
    EMPLOYEE("employee");

    final String description;
}
