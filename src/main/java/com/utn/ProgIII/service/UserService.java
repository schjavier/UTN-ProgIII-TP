package com.utn.ProgIII.service;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;

import java.util.List;

public interface UserService {
    UserWithCredentialDTO createUserWithCredential(CreateUserDTO dto);
    UserWithCredentialDTO getUserById(Long id);
    List<UserWithCredentialDTO> getAllUsers();
    UserWithCredentialDTO updateUser(Long id, CreateUserDTO dto);
    void deleteUser(Long id);
}
