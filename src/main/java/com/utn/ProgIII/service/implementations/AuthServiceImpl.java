package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.LoginRequestDTO;
import com.utn.ProgIII.security.JwtUtil;
import com.utn.ProgIII.repository.CredentialRepository;
import com.utn.ProgIII.security.UserDetailServiceImpl;
import com.utn.ProgIII.service.interfaces.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailServiceImpl userDetailService;
    private JwtUtil jwtUtil;

    @Override
        public String login(LoginRequestDTO loginRequestDTO) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDTO.username(),
                loginRequestDTO.password()
        ));

        UserDetails userDetails = userDetailService.loadUserByUsername(loginRequestDTO.username());
        return jwtUtil.generateToken(userDetails);

    }


}
