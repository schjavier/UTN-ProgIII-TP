package com.utn.ProgIII.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatus {
    ENABLED("Enabled"),
    DISABLED("Disabled");

    final String description;
}
