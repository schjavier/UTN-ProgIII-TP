package com.utn.ProgIII.service;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialsDTO;

import java.util.List;

public interface UserService {
    UserWithCredentialsDTO createUserWithCredentials(CreateUserDTO dto);
    UserWithCredentialsDTO getUserById(int id);
    List<UserWithCredentialsDTO> getAllUsers();
}
