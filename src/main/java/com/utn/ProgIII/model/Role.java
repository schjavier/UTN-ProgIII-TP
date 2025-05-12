package com.utn.ProgIII.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    ADMIN("admin"),
    EMPLOYEE("employee");

    final String description;
}
