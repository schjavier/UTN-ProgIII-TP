package com.utn.ProgIII.service;

import com.utn.ProgIII.dto.GetUserWithCredentialsDTO;
import com.utn.ProgIII.exceptions.CredentialsNotFoundException;
import com.utn.ProgIII.exceptions.UserNotFoundException;
import com.utn.ProgIII.mapper.UserMapper;
import com.utn.ProgIII.model.Credentials.Credentials;
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
    public GetUserWithCredentialsDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado!"));
        Credentials credentials = credentialsRepository.findByProfile(user)
                .orElseThrow(() -> new CredentialsNotFoundException("Credenciales no encontradas!"));

        return userMapper.toUserWithCredentialsDTO(user, credentials);
    }

    @Override
    public List<GetUserWithCredentialsDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<GetUserWithCredentialsDTO> usersWithCredentials = new ArrayList<>();

        for (User user : users) {
            usersWithCredentials.add(userMapper.
                    toUserWithCredentialsDTO(user,credentialsRepository.
                        findByProfile(user).
                        orElseThrow(() -> new CredentialsNotFoundException("Credenciales no encontradas!"))));
        }

        return usersWithCredentials;
    }
}
