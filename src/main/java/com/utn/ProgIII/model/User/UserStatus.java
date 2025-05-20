package com.utn.ProgIII.model.User;

import lombok.AllArgsConstructor;

/**
 * Los estados de usuario existentes en el sistema
 */
@AllArgsConstructor
public enum UserStatus {
    ENABLED("enabled"),
    DISABLED("disabled");

    final String description;
}
