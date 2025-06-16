package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.CreateCredentialDTO;
import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;
import com.utn.ProgIII.exceptions.InvalidRequestException;
import com.utn.ProgIII.exceptions.SelfDeleteUserException;
import com.utn.ProgIII.exceptions.UserNotFoundException;
import com.utn.ProgIII.mapper.UserMapper;
import com.utn.ProgIII.model.Credential.Role;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.model.User.UserStatus;
import com.utn.ProgIII.repository.UserRepository;
import com.utn.ProgIII.validations.CredentialValidations;
import com.utn.ProgIII.validations.UserValidations;
import com.utn.ProgIII.service.interfaces.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que se encarga de la logica entre el repositorio y el mapper
 */
@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidations userValidations;
    private final CredentialValidations credentialValidations;

    @PostConstruct
    public void init() {
        if(!userRepository.existsByCredentialRole(Role.ADMIN)) {
            CreateCredentialDTO testCreds = CreateCredentialDTO.builder()
                    .username("admin")
                    .password("admin1234")
                    .role("ADMIN")
                    .build();

            UserWithCredentialDTO testUser = createUserWithCredential(CreateUserDTO.builder()
                    .firstname("The")
                    .lastname("Administrator")
                    .dni("0000000")
                    .status("ENABLED")
                    .credential(testCreds)
                    .build());

            System.out.println("No se han encontrado administradores, se ha creado uno por defecto");
            System.out.println("El usuario es 'admin' y la contrasenia es 'admin1234'");
        }
    }


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserValidations userValidations,
                           CredentialValidations credentialValidations, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userValidations = userValidations;
        this.credentialValidations = credentialValidations;
        this.passwordEncoder = passwordEncoder;
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

        user.getCredential().setPassword(passwordEncoder.encode(user.getCredential().getPassword()));

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
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        return userMapper.toUserWithCredentialDTO(user);
    }

    /**
     * Muestra los datos de todos los usuarios presentes en el sistema
     *
     * @return Una lista con los DTO de cada usuario existente en el sistema
     * <p>
     */
    @Override
    public List<UserWithCredentialDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserWithCredentialDTO> usersDTO = new ArrayList<UserWithCredentialDTO>();

        for (User user: users) {
            usersDTO.add(userMapper.toUserWithCredentialDTO(user));
        }

        return usersDTO;
    }

    public List<UserWithCredentialDTO> getUsersByStatus(String status)
    {

        List<User> users;
        if(EnumUtils.isValidEnum(UserStatus.class, status))
        {
            users = userRepository.findAllByStatus(UserStatus.valueOf(status));
        } else if (status.equals("ALL")) {
            users = userRepository.findAll();
        } else {
            throw new InvalidRequestException("Ese estado no esta presente");
        }

        return users.stream().map(userMapper::toUserWithCredentialDTO).toList();
    }

    /**
     * Obtiene los datos del usuario solicitado por parametro y los reemplaza por los enviados en la request.
     * @param id El id correspondiente al usuario que se solicito modificar sus datos
     * @param dto El objeto de transferencia con los nuevos datos recibidos desde la request
     * @return Un DTO para mostrar los nuevos datos cargados, como una respuesta
     * @throws UserNotFoundException Si el usuario no existe
     */
    @Override
    @Transactional
    public UserWithCredentialDTO updateUser(Long id, CreateUserDTO dto) {
        User currentUserData = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        User newUserData = userMapper.toEntity(dto);

        userValidations.validateModifiedUserByDni(currentUserData.getDni(), newUserData.getDni());
        credentialValidations.validateModifiedUsernameNotExists(currentUserData.getCredential().getUsername(),
                newUserData.getCredential().getUsername());

        newUserData.setIdUser(currentUserData.getIdUser());
        newUserData.getCredential().setIdCredential(currentUserData.getCredential().getIdCredential());
        newUserData.getCredential().setPassword(passwordEncoder.encode(newUserData.getCredential().getPassword()));

        currentUserData = userRepository.save(newUserData);

        return userMapper.toUserWithCredentialDTO(currentUserData);
    }

    @Override
    public void deleteOrRemoveUser(Long id, String method) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        if(userValidations.checkifRequestedUserIsTheSame(user))
        {
            throw new SelfDeleteUserException("No podes eliminar tu usuario");
        }

        switch (method.toUpperCase())
        {
            case "HARD":
                deleteUserHard(user);
                break;
            case "SOFT":
                deleteUserSoft(user);
                break;
            default:
                throw new InvalidRequestException("La opcion de eliminacion no es correcta");
        }

    }

    /**
     * Hace baja logica del sistema al usuario con el id solicitado por parametro
     * @param user El usuario que se dara de baja temporal
     */
    @Transactional
    private void deleteUserSoft(User user) {
        user.setStatus(UserStatus.DISABLED);
        userRepository.save(user);
    }

    /**
     * Elimina fisicamente del sistema al usuario con el id solicitado por parametro
     * @param user El usuario que se eliminara
     */
    @Transactional
    private void deleteUserHard(User user) {
            userRepository.delete(user);
    }

}
