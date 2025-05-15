package com.utn.ProgIII.service;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;
import com.utn.ProgIII.exceptions.UserNotFoundException;
import com.utn.ProgIII.mapper.UserMapper;
import com.utn.ProgIII.model.Credential.Credential;
import com.utn.ProgIII.model.Credential.Role;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.model.User.UserStatus;
import com.utn.ProgIII.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserWithCredentialDTO createUserWithCredential(CreateUserDTO dto) {
        User user = userMapper.toEntity(dto);
        user = userRepository.save(user);

        return userMapper.toUserWithCredentialDTO(user);
    }

    @Override
    public UserWithCredentialDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado!"));

        return userMapper.toUserWithCredentialDTO(user);
    }

    @Override
    public List<UserWithCredentialDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserWithCredentialDTO> usersWithCredential = new ArrayList<>();

        for (User user : users) {
            usersWithCredential.add(userMapper.
                    toUserWithCredentialDTO(user));
        }

        return usersWithCredential;
    }

    @Override
    @Transactional
    public UserWithCredentialDTO updateUser(Long id, CreateUserDTO dto) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado!"));

        userToUpdate.setFirstname(dto.firstname());
        userToUpdate.setLastname(dto.lastname());
        userToUpdate.setDni(dto.dni());
        userToUpdate.setStatus(UserStatus.valueOf(dto.status()));

        Credential credentialToUpdate = userToUpdate.getCredential();
        credentialToUpdate.setUsername(dto.username());
        credentialToUpdate.setPassword(dto.password());
        credentialToUpdate.setRole(Role.valueOf(dto.role()));

        userToUpdate = userRepository.save(userToUpdate);

        return userMapper.toUserWithCredentialDTO(userToUpdate);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException("Usuario no encontrado!");
        } else {
            userRepository.deleteById(id);
        }
    }
}
