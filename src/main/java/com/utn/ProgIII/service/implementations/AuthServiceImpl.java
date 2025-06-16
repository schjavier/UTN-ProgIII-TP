package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.LoginRequestDTO;
import com.utn.ProgIII.security.JwtUtil;
import com.utn.ProgIII.security.UserDetailServiceImpl;
import com.utn.ProgIII.service.interfaces.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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

    @Override
        public String login(LoginRequestDTO loginRequestDTO) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDTO.username(),
                loginRequestDTO.password()
        ));

        UserDetails userDetails = userDetailService.loadUserByUsername(loginRequestDTO.username());
        return jwtUtil.generateToken(userDetails);

    }

    @Override
    public List<String> getAuthorities() {

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority).toList();
    }

    public boolean hasRole (String roleName)
    {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }

    @Override
    public Long getRoleCount() {
        return (long) SecurityContextHolder.getContext().getAuthentication().getAuthorities().size();
    }
}
