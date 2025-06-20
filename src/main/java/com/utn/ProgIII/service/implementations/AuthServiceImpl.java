package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.LoginRequestDTO;
import com.utn.ProgIII.security.JwtUtil;
import com.utn.ProgIII.security.UserDetailServiceImpl;
import com.utn.ProgIII.service.interfaces.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Un servicio que se encarga de hacer acciones relacionadas a la sesion del usuario
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailService;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserDetailServiceImpl userDetailService,
                           JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Maneja el inicio de sesion
     * @param loginRequestDTO El objeto de inicio de sesion
     * @return Una token de validacion
     */
    @Override
        public String login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDTO.username(),
                loginRequestDTO.password()
        ));

        UserDetails userDetails = userDetailService.loadUserByUsername(loginRequestDTO.username());
        return jwtUtil.generateToken(userDetails);

    }

    /**
     * Verifica si el usuario logeado es un empleado
     * @return un booleano true si lo es
     */
    public boolean isEmployee() {

        return hasRole("ROLE_EMPLOYEE") && (getRoleCount() == 1);
    }

    /**
     * Verifica si el usuario logeado tiene cierto rol
     * @param roleName El nombre del rol (ej ROLE_MANAGER)
     * @return un booleano true si lo tiene
     */
    public boolean hasRole (String roleName)
    {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }

    @Override
    /**
     * Devuelve la cantidad de roles que un usuario tiene
     */
    public Long getRoleCount() {
        return (long) SecurityContextHolder.getContext().getAuthentication().getAuthorities().size();
    }


}
