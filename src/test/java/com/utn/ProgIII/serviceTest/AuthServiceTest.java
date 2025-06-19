package com.utn.ProgIII.serviceTest;

import com.utn.ProgIII.dto.LoginRequestDTO;
import com.utn.ProgIII.security.JwtUtil;
import com.utn.ProgIII.security.UserDetailServiceImpl;
import com.utn.ProgIII.service.implementations.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    private static final String EMPLOYEE_ROLE = "ROLE_EMPLOYEE";
    private static final String ADMIN_ROLE = "ROLE_ADMIN";
//    private static final

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserDetailServiceImpl userDetailService;
    @Mock
    private JwtUtil jwtUtil;


    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;

    @InjectMocks
    AuthServiceImpl authService;

    @BeforeEach
    void setUp(){
        SecurityContextHolder.setContext(securityContext);

    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid(){

        LoginRequestDTO request = new LoginRequestDTO("user", "pass");
        UserDetails userDetails = new User("user", "pass", Collections.emptyList());
        String expectedToken = "mocked.jwt.token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        when(userDetailService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn(expectedToken);

        String token = authService.login(request);

        assertEquals(expectedToken, token);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailService).loadUserByUsername("user");
        verify(jwtUtil).generateToken(userDetails);

    }

    @Test
    public void isEmployee_shouldReturnTrue_whenSingleEmployeeRole(){

        GrantedAuthority authority = new SimpleGrantedAuthority(EMPLOYEE_ROLE);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getAuthorities()).thenAnswer(
                invocation -> Collections.singleton(authority)
        );

        boolean result = authService.isEmployee("ROLE_EMPLOYEE");

        assertTrue(result);

    }

    @Test
    public void isEmployee_shouldReturnFalse_whenMultipleRoles(){
        GrantedAuthority authority = new SimpleGrantedAuthority(EMPLOYEE_ROLE);
        GrantedAuthority authority2 = new SimpleGrantedAuthority(ADMIN_ROLE);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getAuthorities()).thenAnswer(
                invocation -> List.of(authority, authority2)
        );

        boolean result = authService.isEmployee("ROLE_EMPLOYEE");

        assertFalse(result);

    }

    @Test
    public void hasRole_shouldReturnTrue_whenUserHasRole(){
        GrantedAuthority authority = new SimpleGrantedAuthority(EMPLOYEE_ROLE);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getAuthorities()).thenAnswer(
                invocation -> List.of(authority)
        );

        boolean result = authService.hasRole("ROLE_EMPLOYEE");

        assertTrue(result);
    }

    @Test
    public void hasRole_shouldReturnFalse_whenUserHasNotHaveRole(){
        GrantedAuthority authority = new SimpleGrantedAuthority(EMPLOYEE_ROLE);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getAuthorities()).thenAnswer(
                invocation -> List.of(authority)
        );

        boolean result = authService.hasRole("ROLE_ADMIN");

        assertFalse(result);
    }
    @Test
    void roleCount_shouldReturnNumberOfRoles(){
        GrantedAuthority authority = new SimpleGrantedAuthority(EMPLOYEE_ROLE);
        GrantedAuthority authority2 = new SimpleGrantedAuthority(ADMIN_ROLE);


        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getAuthorities()).thenAnswer(
                invocation -> List.of(authority, authority2)
        );

        Long count = authService.getRoleCount();

        assertEquals(2L, count);

    }

}
