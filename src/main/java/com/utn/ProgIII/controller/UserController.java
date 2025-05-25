package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;
import com.utn.ProgIII.service.interfaces.UserService;
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
    @PostMapping()
    public ResponseEntity<UserWithCredentialDTO> createUser(@RequestBody CreateUserDTO dto) {
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
    @PutMapping()
    public ResponseEntity<UserWithCredentialDTO> updateUser(@RequestParam Long id, @RequestBody CreateUserDTO dto) {
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
    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id, @RequestParam(defaultValue = "soft") String deletionType) {
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
