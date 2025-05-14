package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialsDTO;
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
    public ResponseEntity<UserWithCredentialsDTO> getUserById(@PathVariable int id) {
        UserWithCredentialsDTO response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<UserWithCredentialsDTO>> getAllUsers() {
        List<UserWithCredentialsDTO> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<UserWithCredentialsDTO> createProduct(@RequestBody CreateUserDTO dto) {
        UserWithCredentialsDTO response = userService.createUserWithCredentials(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
