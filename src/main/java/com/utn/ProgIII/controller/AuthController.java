package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.LoginRequestDTO;
import com.utn.ProgIII.dto.LoginResponseDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;
import com.utn.ProgIII.service.interfaces.AuthService;
import com.utn.ProgIII.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/auth")
@Tag(name = "Sesion y cuenta", description = "Todo lo relacionado a los usuarios")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/signup")
    @ApiResponse(responseCode = "201", description = "Usuario creado", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = UserWithCredentialDTO.class)
    ))
    @ApiResponse(responseCode = "400", description = "Datos malformados", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "(Mensaje de errores del usuario)")
    ))
    @ApiResponse(responseCode = "409", description = "Usuario/dni existente", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "(mensaje informando el usuario)")
    ))
    public ResponseEntity<UserWithCredentialDTO> userSignup(@RequestBody CreateUserDTO createUserDTO){
        UserWithCredentialDTO response = userService.createUserWithCredential(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
