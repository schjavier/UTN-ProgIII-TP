package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.LoginRequestDTO;
import com.utn.ProgIII.dto.LoginResponseDTO;
import com.utn.ProgIII.service.interfaces.AuthService;
import com.utn.ProgIII.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@Tag(name = "Sesion y cuenta", description = "Inicio de sesion")
/**
 * Clase para manejar requests de manejo de cuentas
 */
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Inicio de sesion", description = "Metodo para iniciar seción")
    @ApiResponse(responseCode = "200", description = "Retorna un token", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(implementation = LoginResponseDTO.class)
    ))
    @ApiResponse(responseCode = "201", description = "Usuario inexistente", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "El usuario y la contraseña no coinciden")
    ))
    public ResponseEntity<LoginResponseDTO> userLogin(@RequestBody LoginRequestDTO loginRequest) {

        String token = authService.login(loginRequest);

        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

}
