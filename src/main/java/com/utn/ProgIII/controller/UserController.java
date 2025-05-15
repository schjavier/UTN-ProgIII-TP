package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;
import com.utn.ProgIII.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserWithCredentialDTO> getUserById(@PathVariable Long id) {
        UserWithCredentialDTO response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<UserWithCredentialDTO>> getAllUsers() {
        List<UserWithCredentialDTO> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<UserWithCredentialDTO> createUser(@RequestBody CreateUserDTO dto) {
        UserWithCredentialDTO response = userService.createUserWithCredential(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserWithCredentialDTO> updateUser(@PathVariable Long id, @RequestBody CreateUserDTO dto) {
        UserWithCredentialDTO response = userService.updateUser(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
