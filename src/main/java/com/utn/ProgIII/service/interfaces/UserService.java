package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;
import com.utn.ProgIII.model.User.User;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Interfaz que dicta los comportamientos que debe incorporar el servicio de usuarios
 */
public interface UserService {
    UserWithCredentialDTO createUserWithCredential(CreateUserDTO dto);
    UserWithCredentialDTO getUserById(Long id);

    List<UserWithCredentialDTO> getAllUsers();

    List<UserWithCredentialDTO> getUsersByStatus(String status);

    UserWithCredentialDTO updateUser(Long id, CreateUserDTO dto);


    void deleteOrRemoveUser(Long id, String method);
}
