package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.LoginRequestDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    String login(LoginRequestDTO loginRequestDTO);

}
