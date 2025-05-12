package com.utn.ProgIII.service;

import com.utn.ProgIII.dto.GetUserWithCredentialsDTO;

import java.util.List;

public interface UserService {
    GetUserWithCredentialsDTO getUserById(int id);
    List<GetUserWithCredentialsDTO> getAllUsers();
}
