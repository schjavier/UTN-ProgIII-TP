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
    @GetMapping("/{id}")
    public ResponseEntity<UserWithCredentialDTO> getUserById(@PathVariable Long id) {
        UserWithCredentialDTO response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Muestra los datos de todos los usuarios presentes en el sistema
     * @return Una respuesta en formato json con los datos de todos los usuarios existentes en el sistema
     */
    @GetMapping()
    public ResponseEntity<List<UserWithCredentialDTO>> getAllUsers() {
        List<UserWithCredentialDTO> response = userService.getAllUsers();
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
     * Apto para baja logica
     * @param id El id correspondiente al usuario que se solicito modificar sus datos
     * @param dto El objeto de transferencia con los nuevos datos creado a partir del cuerpo de la request
     * @return Una respuesta en formato json para mostrar los nuevos datos del usuario modificado
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserWithCredentialDTO> updateUser(@PathVariable Long id, @RequestBody CreateUserDTO dto) {
        UserWithCredentialDTO response = userService.updateUser(id, dto);
        return ResponseEntity.ok().body(response);
    }

    /**
     * Elimina al usuario con el id enviado por parametro del sistema
     * @param id El id correspondiente al usuario que se solicito eliminar
     * @return Una respuesta sin contenido (HTTP 204) para indicar que no hubo errores
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
