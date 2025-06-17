package com.utn.ProgIII.model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

/**
 * Los estados de usuario existentes en el sistema
 */
@AllArgsConstructor
@Schema(defaultValue = "ENABLED")
public enum UserStatus {
    ENABLED("enabled"),
    DISABLED("disabled");

    final String description;
}
