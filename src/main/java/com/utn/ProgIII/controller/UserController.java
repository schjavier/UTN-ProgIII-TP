package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.GetUserWithCredentialsDTO;
import com.utn.ProgIII.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<GetUserWithCredentialsDTO> getUserById(@PathVariable int id) {
        GetUserWithCredentialsDTO response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<GetUserWithCredentialsDTO>> getAllUsers() {
        List<GetUserWithCredentialsDTO> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }
}
