package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.LoginRequestDTO;
import com.utn.ProgIII.dto.LoginResponseDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;
import com.utn.ProgIII.service.interfaces.AuthService;
import com.utn.ProgIII.service.interfaces.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventPublicationInterceptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> userLogin(@RequestBody LoginRequestDTO loginRequest) {
        String token = authService.login(loginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token)) ;

    }

    @PostMapping("/signup")
    public ResponseEntity<UserWithCredentialDTO> userSignup(@RequestBody CreateUserDTO createUserDTO){
        UserWithCredentialDTO response = userService.createUserWithCredential(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
