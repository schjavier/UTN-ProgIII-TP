package com.utn.ProgIII.model.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatus {
    ENABLED("enabled"),
    DISABLED("disabled");

    final String description;
}
