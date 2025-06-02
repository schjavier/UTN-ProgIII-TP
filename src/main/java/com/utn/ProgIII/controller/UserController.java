package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import com.utn.ProgIII.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase que se encarga del procesamiento de las solicitudes recibidas desde el front
 */
@RestController
@RequestMapping("/user")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Devuelve los datos del usuario con el id enviado por parametro
     * @param id El id del usuario que se desea ver su informacion
     * @return Una respuesta en formato json para mostrar los datos del usuario deseado
     */
    @Operation(summary = "Obtener un usuario por id", description = "Obtiene un usuario mediante id")
    @ApiResponse(responseCode = "200",description = "Usuario encontrado", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(implementation = UserWithCredentialDTO.class)
    ))
    @ApiResponse(responseCode = "404",description = "Usuario no encontrado", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "Usuario no encontrado")
    ))
    @GetMapping()
    public ResponseEntity<UserWithCredentialDTO> getUserById(@RequestParam Long id) {
        UserWithCredentialDTO response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Muestra los datos de los usuarios según su estado (activo, dado de baja o ambos)
     * @param status El estado de los usuarios que se desea ver (ENABLED, DISABLED o ALL (todos))
     * @return Una respuesta en formato json con los datos de los usuarios filtrados por estado
     */
    @Operation(summary = "Obtener una lista de usuarios segun su status", description = "Retorna una lista de usuarios por estado")
    @ApiResponse(responseCode = "200",description = "Usuarios encontrados",content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = UserWithCredentialDTO.class))
    ))
    @ApiResponse(responseCode = "400",description = "Pedido malformado", content = @Content())
    @GetMapping("/filter")
    public ResponseEntity<List<UserWithCredentialDTO>> getUsersByStatus(
            @RequestParam(defaultValue = "ENABLED") String status) {
        List<UserWithCredentialDTO> response;
        if (status.equalsIgnoreCase("ENABLED")) {
            response = userService.getEnabledUsers();
        } else if (status.equalsIgnoreCase("DISABLED")) {
            response = userService.getDisabledUsers();
        } else if (status.equalsIgnoreCase("ALL")) {
            response = userService.getAllUsers();
        } else {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Se crea un nuevo usuario y credenciales en el sistema a partir de la informacion recibida en el cuerpo de la request
     * @param dto El objeto de transferencia recibido desde la request
     * @return Un DTO para mostrar los datos cargados y su id correspondiente, como una respuesta
     */
    @Operation(summary = "Crea un usuario", description = "Crea un usuario con sus credenciales")
    @ApiResponse(responseCode = "201", description = "Usuario creado")
    @ApiResponse(responseCode = "400", description = "Error en datos insertados", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "(Un mensaje de los errores del usuario)")
    ))
    @ApiResponse(responseCode = "409", description = "Usuario existente por dni", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(examples = {"El nombre de usuario ya existe en la base de datos", "El dni ingresado ya se encuentra registrado"})
    ))
    @PostMapping()
    public ResponseEntity<UserWithCredentialDTO> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Los datos del usuario nuevo")
            @RequestBody CreateUserDTO dto
    ) {
        UserWithCredentialDTO response = userService.createUserWithCredential(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtiene los datos del usuario con el id enviado por parametro y los reemplaza por los enviados en el
     * cuerpo de la request
     * @param id El id correspondiente al usuario que se solicito modificar sus datos
     * @param dto El objeto de transferencia con los nuevos datos creado a partir del cuerpo de la request
     * @return Una respuesta en formato json para mostrar los nuevos datos del usuario modificado
     */
    @Operation(summary = "Actualiza un usuario", description = "Actualiza un usuario con los datos introducidos")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "400",description = "Error en datos insertados", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(examples = {"El nombre de usuario ya existe en la base de datos", "El dni ingresado ya se encuentra registrado"})
    ))
    @PutMapping()
    public ResponseEntity<UserWithCredentialDTO> updateUser(@RequestParam Long id,
                                                            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Los datos cambiados del usuario")
                                                            @RequestBody CreateUserDTO dto) {
        UserWithCredentialDTO response = userService.updateUser(id, dto);
        return ResponseEntity.ok().body(response);
    }

    /**
     * Elimina al usuario con el id enviado por parametro del sistema, pudiendo especificar si es por medio
     * de baja logica o fisica
     * @param id El id correspondiente al usuario que se solicito eliminar
     * @param deletionType El tipo de eliminacion (dura/fisica o blanda/logica)
     * @return Una respuesta sin contenido (HTTP 204) para indicar que no hubo errores
     */
    @Operation(summary = "Elimina (hard) o hace una Baja logica (soft)", description = "Hace una baja logica o una eliminacion del usuario.")
    @ApiResponse(responseCode = "204", description = "Eliminacion correcta", content = @Content())
    @ApiResponse(responseCode = "400", description = "Error en datos insertados", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "Usuario no encontrado")
    ))
    @DeleteMapping()
    public ResponseEntity<?> delete(
            @RequestParam Long id,
            @RequestParam(defaultValue = "soft") String deletionType) {
        if (deletionType.equalsIgnoreCase("soft")) {
            userService.deleteUserSoft(id);
        } else if (deletionType.equalsIgnoreCase("hard")) {
            userService.deleteUserHard(id);
        } else {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }
}
