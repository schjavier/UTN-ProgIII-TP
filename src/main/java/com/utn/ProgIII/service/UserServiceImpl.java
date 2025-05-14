package com.utn.ProgIII.service;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialsDTO;
import com.utn.ProgIII.exceptions.CredentialsNotFoundException;
import com.utn.ProgIII.exceptions.UserNotFoundException;
import com.utn.ProgIII.mapper.UserMapper;
import com.utn.ProgIII.model.Credentials.Credentials;
import com.utn.ProgIII.model.Credentials.Role;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.repository.CredentialsRepository;
import com.utn.ProgIII.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CredentialsRepository credentialsRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, CredentialsRepository credentialsRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserWithCredentialsDTO createUserWithCredentials(CreateUserDTO dto) {
        User user = userMapper.toEntity(dto);
        user = userRepository.save(user);

        Credentials credentials = new Credentials();
        credentials.setUsername(dto.username());
        credentials.setPassword(dto.password());
        credentials.setRole(dto.role().isBlank() ? Role.EMPLOYEE : Role.valueOf(dto.role()));
        credentials.setProfile(user);
        credentialsRepository.save(credentials);

        return userMapper.toUserWithCredentialsDTO(user, credentials);
    }

    @Override
    public UserWithCredentialsDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado!"));
        Credentials credentials = credentialsRepository.findByProfile(user)
                .orElseThrow(() -> new CredentialsNotFoundException("Credenciales no encontradas!"));

        return userMapper.toUserWithCredentialsDTO(user, credentials);
    }

    @Override
    public List<UserWithCredentialsDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserWithCredentialsDTO> usersWithCredentials = new ArrayList<>();

        for (User user : users) {
            usersWithCredentials.add(userMapper.
                    toUserWithCredentialsDTO(user,credentialsRepository.
                        findByProfile(user).
                        orElseThrow(() -> new CredentialsNotFoundException("Credenciales no encontradas!"))));
        }

        return usersWithCredentials;
    }
}
