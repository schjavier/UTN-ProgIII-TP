package com.utn.ProgIII.serviceTest;

import com.utn.ProgIII.dto.CreateCredentialDTO;
import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.exceptions.DuplicateEntityException;
import com.utn.ProgIII.mapper.UserMapper;
import com.utn.ProgIII.model.Credential.Credential;
import com.utn.ProgIII.model.Credential.Role;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.model.User.UserStatus;
import com.utn.ProgIII.repository.UserRepository;
import com.utn.ProgIII.service.implementations.UserServiceImpl;
import com.utn.ProgIII.validations.CredentialValidations;
import com.utn.ProgIII.validations.UserValidations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    UserValidations userValidations;

    @Mock
    CredentialValidations credentialValidations;

    @InjectMocks
    UserServiceImpl userService;

    private static final String FIRSTNAME = "TESTING";
    private static final String LASTNAME = "ACCOUNT";
    private static final String DNI = "9999999";
    private static final Long USER_ID = 1L;
    private static final UserStatus ENABLED = UserStatus.ENABLED;
    private static final String USERNAME = "testinguser";
    private static final String PASSWORD = "test1234";
    private static final Role ADMIN_ROLE = Role.ADMIN;

    private User adminUser;
    private CreateUserDTO userDTO;

    private Credential createAdminCredential() {
        Credential credential = new Credential();

        credential.setUsername(USERNAME);
        credential.setPassword(PASSWORD);
        credential.setRole(ADMIN_ROLE);

        return credential;
    }

    private User createAdminUser() {
        User user = new User();

        user.setFirstname(FIRSTNAME);
        user.setLastname(LASTNAME);
        user.setDni(DNI);
        user.setStatus(ENABLED);
        user.setCredential(createAdminCredential());

        return user;
    }

    private CreateCredentialDTO createValidCredentialDTO() {
        return new CreateCredentialDTO(USERNAME,PASSWORD,ADMIN_ROLE.toString());
    }

    private CreateUserDTO createValidUserDTO() {
        return new CreateUserDTO(FIRSTNAME,LASTNAME, DNI,ENABLED.toString(),createValidCredentialDTO());
    }

    @BeforeEach
    void setUp() {
        adminUser = createAdminUser();
        userDTO = createValidUserDTO();
    }

    @Test
    void createUser_shouldThrowException_whenDniExists() {
        when(userMapper.toEntity(userDTO)).thenReturn(adminUser);

        doThrow(new DuplicateEntityException("El usuario con ese dni ya existe en la base de datos"))
                .when(userValidations).validateUserByDni(adminUser.getDni());

        assertThrows(DuplicateEntityException.class,
                () -> userService.createUserWithCredential(createValidUserDTO()));

        verify(userValidations).validateUserByDni(adminUser.getDni());
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toUserWithCredentialDTO(any());
    }

    @Test
    void updateUser_shouldThrowException_whenUpdatingWithExistingDni() {
        Long userId = USER_ID;
        String newDni = "1234567";

        CreateUserDTO updatedDTO = new CreateUserDTO(
                FIRSTNAME, LASTNAME, newDni, ENABLED.toString(), createValidCredentialDTO());

        User userToUpdate = createAdminUser();
        User updatedUser = createAdminUser();
        updatedUser.setDni(newDni);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(userToUpdate));
        when(userMapper.toEntity(updatedDTO)).thenReturn(updatedUser);

        doThrow(new DuplicateEntityException("El DNI ingresado ya se encuentra registrado"))
                .when(userValidations).validateModifiedUserByDni(userToUpdate.getDni(), newDni);

        assertThrows(DuplicateEntityException.class,
                () -> userService.updateUser(userId, updatedDTO));

        verify(userRepository).findById(userId);
        verify(userValidations).validateModifiedUserByDni(userToUpdate.getDni(), newDni);
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toUserWithCredentialDTO(any());
    }

    @Test
    void updateUser_shouldNotThrowException_whenDniUnchanged() {
        CreateUserDTO updatedDTO = new CreateUserDTO(
                FIRSTNAME, LASTNAME, DNI, ENABLED.toString(), createValidCredentialDTO());

        User userToUpdate = createAdminUser();
        User updatedUser = createAdminUser();

        when(userRepository.findById(USER_ID)).thenReturn(java.util.Optional.of(userToUpdate));
        when(userMapper.toEntity(updatedDTO)).thenReturn(updatedUser);

        assertDoesNotThrow(() -> userService.updateUser(USER_ID, updatedDTO));

        verify(userRepository).findById(USER_ID);
        verify(userValidations).validateModifiedUserByDni(userToUpdate.getDni(), updatedUser.getDni());
        verify(userMapper).toUserWithCredentialDTO(any());
    }

    @Test
    void createUser_shouldThrowException_whenUsernameExists() {
        when(userMapper.toEntity(userDTO)).thenReturn(adminUser);

        doThrow(new DuplicateEntityException("El nombre de usuario ya existe en la base de datos"))
                .when(credentialValidations).validateUsernameNotExists(adminUser.getCredential().getUsername());

        assertThrows(DuplicateEntityException.class,
                () -> userService.createUserWithCredential(userDTO));

        verify(credentialValidations).validateUsernameNotExists(adminUser.getCredential().getUsername());
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toUserWithCredentialDTO(any());
    }

    @Test
    void updateUser_shouldThrowException_whenUpdatingWithExistingUsername() {
        String modifiedUsername = "PRUEBITA";

        CreateCredentialDTO modifiedCredentialDTO = new CreateCredentialDTO(
                modifiedUsername, PASSWORD, ADMIN_ROLE.toString());
        CreateUserDTO userWithModifiedCredentialDTO = new CreateUserDTO(
                FIRSTNAME, LASTNAME, DNI, ENABLED.toString(), modifiedCredentialDTO);

        User userToUpdate = createAdminUser();
        User updatedUser = createAdminUser();
        updatedUser.getCredential().setUsername(modifiedUsername);

        when(userRepository.findById(USER_ID)).thenReturn(java.util.Optional.of(userToUpdate));
        when(userMapper.toEntity(userWithModifiedCredentialDTO)).thenReturn(updatedUser);

        doThrow(new DuplicateEntityException("El nombre de usuario ingresado ya existe en la base de datos"))
                .when(credentialValidations).validateModifiedUsernameNotExists(
                        USERNAME, modifiedUsername);

        assertThrows(DuplicateEntityException.class,
                () -> userService.updateUser(USER_ID, userWithModifiedCredentialDTO));

        verify(userRepository).findById(USER_ID);
        verify(credentialValidations).validateModifiedUsernameNotExists(USERNAME, modifiedUsername);
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toUserWithCredentialDTO(any());
    }

    @Test
    void updateUser_shouldNotThrowException_whenUsernameUnchanged() {
        CreateCredentialDTO sameCredentialDTO = new CreateCredentialDTO(
                USERNAME, PASSWORD, ADMIN_ROLE.toString());
        CreateUserDTO updatedDTO = new CreateUserDTO(
                FIRSTNAME, LASTNAME, DNI, ENABLED.toString(), sameCredentialDTO);

        User userToUpdate = createAdminUser();
        User updatedUser = createAdminUser();

        when(userRepository.findById(USER_ID)).thenReturn(java.util.Optional.of(userToUpdate));
        when(userMapper.toEntity(updatedDTO)).thenReturn(updatedUser);

        assertDoesNotThrow(() -> userService.updateUser(USER_ID, updatedDTO));

        verify(userRepository).findById(USER_ID);
        verify(credentialValidations).validateModifiedUsernameNotExists(USERNAME, USERNAME);
        verify(userRepository).save(updatedUser);
    }

}