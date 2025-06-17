package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.LoginRequestDTO;

import java.util.List;

public interface AuthService {

    String login(LoginRequestDTO loginRequestDTO);
    List<String> getAuthorities();
    boolean hasRole(String role);
    Long getRoleCount();

}
