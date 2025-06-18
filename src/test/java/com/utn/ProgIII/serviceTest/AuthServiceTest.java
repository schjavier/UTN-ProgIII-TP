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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @InjectMocks
    AuthServiceImpl authService;

    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;


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
    void getAuthorities_shouldReturnListOfAuthorities(){
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_EMPLOYEE");

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(authority);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getAuthorities()).thenReturn();

        // hay problemas con thenReturn
        //estoy trabado hasta aca con este metodo! ya va a salir!

    }

    @Test
    public void isEmployee_shouldReturnTrue_WhenUserHasRoleEmployee(){

    }

}
