package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;
import com.utn.ProgIII.exceptions.UserNotFoundException;
import com.utn.ProgIII.mapper.UserMapper;
import com.utn.ProgIII.model.Credential.Credential;
import com.utn.ProgIII.model.Credential.Role;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.model.User.UserStatus;
import com.utn.ProgIII.repository.UserRepository;
import com.utn.ProgIII.validations.CredentialValidations;
import com.utn.ProgIII.validations.UserValidations;
import com.utn.ProgIII.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que se encarga de la logica entre el repositorio y el mapper
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidations userValidations;
    private final CredentialValidations credentialValidations;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserValidations userValidations, CredentialValidations credentialValidations) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userValidations = userValidations;
        this.credentialValidations = credentialValidations;
    }

    /**
     * Se crea un nuevo usuario y credenciales en el sistema, a partir de la informacion recibida en la request
     * @param dto El objeto de transferencia recibido desde la request
     * @return Un DTO para mostrar los datos cargados y su id correspondiente, como una respuesta
     */
    @Override
    @Transactional
    public UserWithCredentialDTO createUserWithCredential(CreateUserDTO dto) {
        User user = userMapper.toEntity(dto);

        userValidations.validateUserByDni(dto.dni());
        credentialValidations.validateUsernameNotExists(dto.credential().username());

        user = userRepository.save(user);

        return userMapper.toUserWithCredentialDTO(user);
    }

    /**
     * Muestra los datos del usuario con el id solicitado por parametro
     * @param id El id correspondiente al usuario que se solicito ver sus datos
     * @return Un DTO para mostrar la informacion de tal usuario
     * @throws UserNotFoundException Si el usuario no existe
     */
    @Override
    public UserWithCredentialDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado!"));

        return userMapper.toUserWithCredentialDTO(user);
    }

    /**
     * Muestra los datos de todos los usuarios presentes en el sistema
     * @return Una lista con los DTO de cada usuario existente en el sistema
     */
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

    /**
     * Obtiene los datos del usuario solicitado por parametro y los reemplaza por los enviados en la request.
     * Apto para baja logica
     * @param id El id correspondiente al usuario que se solicito modificar sus datos
     * @param dto El objeto de transferencia con los nuevos datos recibidos desde la request
     * @return Un DTO para mostrar los nuevos datos cargados, como una respuesta
     * @throws UserNotFoundException Si el usuario no existe
     */
    @Override
    @Transactional
    public UserWithCredentialDTO updateUser(Long id, CreateUserDTO dto) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        userValidations.validateModifiedUserByDni(userToUpdate.getDni(),dto.dni());
        credentialValidations.validateModifiedUsernameNotExists(userToUpdate.getCredential().getUsername(),
                dto.credential().username());

        userToUpdate.setFirstname(dto.firstname());
        userToUpdate.setLastname(dto.lastname());
        userToUpdate.setDni(dto.dni());
        userToUpdate.setStatus(UserStatus.valueOf(dto.status()));

        Credential credentialToUpdate = userToUpdate.getCredential();
        credentialToUpdate.setUsername(dto.credential().username());
        credentialToUpdate.setPassword(dto.credential().password());
        credentialToUpdate.setRole(Role.valueOf(dto.credential().role()));

        userToUpdate = userRepository.save(userToUpdate);

        return userMapper.toUserWithCredentialDTO(userToUpdate);
    }

    /**
     * Elimina fisicamente del sistema al usuario con el id solicitado por parametro
     * @param id El id correspondiente al usuario que se solicito eliminar
     */
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
