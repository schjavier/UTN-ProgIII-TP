package com.utn.ProgIII.model.Credentials;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    ADMIN("admin"),
    EMPLOYEE("employee");

    final String description;
}
