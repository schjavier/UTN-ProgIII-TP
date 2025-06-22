package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.LoginRequestDTO;
import com.utn.ProgIII.dto.LoginResponseDTO;
import com.utn.ProgIII.service.interfaces.AuthService;
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

/**
 * Clase para manejar requests de manejo de cuentas
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Sesión y cuenta", description = "Inicio de sesión")
@ApiResponse(responseCode = "403", description = "Dirección no encontrada", content = @Content())
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Inicio de sesión", description = "Método para iniciar sesión")
    @ApiResponse(responseCode = "200", description = "Retorna un token", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(implementation = LoginResponseDTO.class)
    ))
    @ApiResponse(responseCode = "201", description = "Usuario inexistente", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "Usuario no encontrado")
    ))
    public ResponseEntity<LoginResponseDTO> userLogin(@RequestBody LoginRequestDTO loginRequest) {

        String token = authService.login(loginRequest);

        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

}
