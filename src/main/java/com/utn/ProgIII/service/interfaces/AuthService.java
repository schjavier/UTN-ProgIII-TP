package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.LoginRequestDTO;

public interface AuthService {

    String login(LoginRequestDTO loginRequestDTO);
    boolean isEmployee();
    boolean hasRole(String role);
    Long getRoleCount();

}
