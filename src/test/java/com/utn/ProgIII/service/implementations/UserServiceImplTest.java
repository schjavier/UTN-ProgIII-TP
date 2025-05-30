package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.CreateCredentialDTO;
import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.mapper.UserMapper;
import com.utn.ProgIII.model.Credential.Role;
import com.utn.ProgIII.model.User.UserStatus;
import com.utn.ProgIII.repository.UserRepository;
import com.utn.ProgIII.validations.CredentialValidations;
import com.utn.ProgIII.validations.UserValidations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserValidations userValidations;
    private CredentialValidations credentialValidations;
    private CreateUserDTO userDto;
    private CreateCredentialDTO credDto;

    @Autowired
    public UserServiceImplTest(UserRepository userRepository, UserMapper userMapper, UserValidations userValidations, CredentialValidations credentialValidations) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userValidations = userValidations;
        this.credentialValidations = credentialValidations;
    }

    @Test
    void createUserWithCredential() {
        credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("")
                .role(String.valueOf(Role.EMPLOYEE))
                .build();

        userDto = CreateUserDTO.builder()
                .firstname("Usuario")
                .lastname("Prueba")
                .dni("11222333")
                .status(String.valueOf(UserStatus.ENABLED))
                .credential(credDto)
                .build();
    }

    @Test
    void getUserById() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getEnabledUsers() {
    }

    @Test
    void getDisabledUsers() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUserSoft() {
    }

    @Test
    void deleteUserHard() {
    }
}