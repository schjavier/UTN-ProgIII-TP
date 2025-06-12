package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.CreateCredentialDTO;
import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;
import com.utn.ProgIII.exceptions.InvalidRequestException;
import com.utn.ProgIII.model.Credential.Credential;
import com.utn.ProgIII.model.Credential.Role;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.model.User.UserStatus;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserMapperTest {
    private UserMapper mapper;
    private User user;
    private Credential credential;
    private Validator validator;

    @BeforeEach
    void setUp() {
        mapper = new UserMapper(new BCryptPasswordEncoder());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void userToDto_AllFieldsMappedFine() {
        credential = Credential.builder()
                .idCredential(1L)
                .username("prueba")
                .password("12345678")
                .role(Role.EMPLOYEE)
                .build();

        user = User.builder()
                .idUser(1L)
                .firstname("Prueba")
                .lastname("Usuario")
                .dni("1234567")
                .status(UserStatus.ENABLED)
                .credential(credential)
                .build();

        UserWithCredentialDTO dto = mapper.toUserWithCredentialDTO(user);

        assertThat(dto)
                .hasNoNullFieldsOrProperties()
                .satisfies(result -> {
                    assertThat(result.idUser()).isEqualTo(user.getIdUser());
                    assertThat(result.firstname()).isEqualTo(user.getFirstname());
                    assertThat(result.lastname()).isEqualTo(user.getLastname());
                    assertThat(result.dni()).isEqualTo(user.getDni());
                    assertThat(result.status()).isEqualTo(user.getStatus().toString());
                    assertThat(result.credential()).isEqualTo(user.getCredential());
                });
    }

    @Test
    void dtoToUser_AllFieldsMappedFine() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Usuario")
                .dni("1234567")
                .status("ENABLED")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        assertThat(result)
                .hasNoNullFieldsOrPropertiesExcept("idUser","idCredential")
                .satisfies(mappedEntity -> {

                    assertThat(mappedEntity.getFirstname()).isEqualTo(userDto.firstname());
                    assertThat(mappedEntity.getLastname()).isEqualTo(userDto.lastname());
                    assertThat(mappedEntity.getDni()).isEqualTo(userDto.dni());
                    assertThat(mappedEntity.getStatus().toString()).isEqualTo(userDto.status().toUpperCase());

                    assertThat(mappedEntity.getCredential().getUsername()).isEqualTo(credDto.username());
                    assertThat(mappedEntity.getCredential().getPassword()).isEqualTo(credDto.password());
                    assertThat(mappedEntity.getCredential().getRole().toString()).isEqualTo(credDto.role().toUpperCase());
                });
    }

    @Test
    void dtoToUser_AllFieldsMappedFine_WithoutStatus() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Usuario")
                .dni("1234567")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        assertThat(result)
                .hasNoNullFieldsOrPropertiesExcept("idUser","idCredential")
                .satisfies(mappedEntity -> {

                    assertThat(mappedEntity.getFirstname()).isEqualTo(userDto.firstname());
                    assertThat(mappedEntity.getLastname()).isEqualTo(userDto.lastname());
                    assertThat(mappedEntity.getDni()).isEqualTo(userDto.dni());
                    assertThat(mappedEntity.getStatus()).isEqualTo(UserStatus.ENABLED);

                    assertThat(mappedEntity.getCredential().getUsername()).isEqualTo(credDto.username());
                    assertThat(mappedEntity.getCredential().getPassword()).isEqualTo(credDto.password());
                    assertThat(mappedEntity.getCredential().getRole().toString()).isEqualTo(credDto.role().toUpperCase());
                });
    }

    @Test
    void dtoToUser_AllFieldsMappedFine_WithoutRole() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Usuario")
                .dni("1234567")
                .status("ENABLED")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        assertThat(result)
                .hasNoNullFieldsOrPropertiesExcept("idUser","idCredential")
                .satisfies(mappedEntity -> {

                    assertThat(mappedEntity.getFirstname()).isEqualTo(userDto.firstname());
                    assertThat(mappedEntity.getLastname()).isEqualTo(userDto.lastname());
                    assertThat(mappedEntity.getDni()).isEqualTo(userDto.dni());
                    assertThat(mappedEntity.getStatus().toString()).isEqualTo(userDto.status());

                    assertThat(mappedEntity.getCredential().getUsername()).isEqualTo(credDto.username());
                    assertThat(mappedEntity.getCredential().getPassword()).isEqualTo(credDto.password());
                    assertThat(mappedEntity.getCredential().getRole()).isEqualTo(Role.EMPLOYEE);
                });
    }

    @Test
    void dtoToUser_AllFieldsMappedFine_WithoutStatusAndRole() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Usuario")
                .dni("1234567")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        assertThat(result)
                .hasNoNullFieldsOrPropertiesExcept("idUser","idCredential")
                .satisfies(mappedEntity -> {

                    assertThat(mappedEntity.getFirstname()).isEqualTo(userDto.firstname());
                    assertThat(mappedEntity.getLastname()).isEqualTo(userDto.lastname());
                    assertThat(mappedEntity.getDni()).isEqualTo(userDto.dni());
                    assertThat(mappedEntity.getStatus()).isEqualTo(UserStatus.ENABLED);

                    assertThat(mappedEntity.getCredential().getUsername()).isEqualTo(credDto.username());
                    assertThat(mappedEntity.getCredential().getPassword()).isEqualTo(credDto.password());
                    assertThat(mappedEntity.getCredential().getRole()).isEqualTo(Role.EMPLOYEE);
                });
    }

    @Test
    void dtoToUser_FirstnameContainsInvalidCharacters() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueb@")
                .lastname("Usuario")
                .dni("1234567")
                .status("ENABLED")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        Set<ConstraintViolation<User>> violations = validator.validate(result);
        assertThat(violations).hasSize(1);
    }

    @Test
    void dtoToUser_FirstnameIsTooShort() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Ab")
                .lastname("Usuario")
                .dni("1234567")
                .status("ENABLED")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        Set<ConstraintViolation<User>> violations = validator.validate(result);
        assertThat(violations).hasSize(1);
    }

    @Test
    void dtoToUser_FirstnameIsTooLong() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz")
                .lastname("Usuario")
                .dni("1234567")
                .status("ENABLED")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        Set<ConstraintViolation<User>> violations = validator.validate(result);
        assertThat(violations).hasSize(1);
    }

    @Test
    void dtoToUser_FirstNameInitialIsInLowercase() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("prueba")
                .lastname("Usuario")
                .dni("1234567")
                .status("ENABLED")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        Set<ConstraintViolation<User>> violations = validator.validate(result);
        assertThat(violations).hasSize(1);
    }

    @Test
    void dtoToUser_FirstNameTwoInitialsAreInLowercase() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("prueba usuario")
                .lastname("Usuario")
                .dni("1234567")
                .status("ENABLED")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        Set<ConstraintViolation<User>> violations = validator.validate(result);
        assertThat(violations).hasSize(1);
    }

    @Test
    void dtoToUser_LastnameContainsInvalidCharacters() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("usuari0")
                .dni("1234567")
                .status("ENABLED")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        Set<ConstraintViolation<User>> violations = validator.validate(result);
        assertThat(violations).hasSize(1);
    }

    @Test
    void dtoToUser_LastnameIsTooShort() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Us")
                .dni("1234567")
                .status("ENABLED")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        Set<ConstraintViolation<User>> violations = validator.validate(result);
        assertThat(violations).hasSize(1);
    }

    @Test
    void dtoToUser_LastnameIsTooLong() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz")
                .dni("1234567")
                .status("ENABLED")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        Set<ConstraintViolation<User>> violations = validator.validate(result);
        assertThat(violations).hasSize(1);
    }

    @Test
    void dtoToUser_LastnameInitialIsInLowercase() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("usuario")
                .dni("1234567")
                .status("ENABLED")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        Set<ConstraintViolation<User>> violations = validator.validate(result);
        assertThat(violations).hasSize(1);
    }

    @Test
    void dtoToUser_LastnameTwoInitialsAreInLowercase() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("usuario sistema")
                .dni("1234567")
                .status("ENABLED")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        Set<ConstraintViolation<User>> violations = validator.validate(result);
        assertThat(violations).hasSize(1);
    }

    @Test
    void dtoToUser_DniContainsLetters() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Usuario")
                .dni("1234567C")
                .status("ENABLED")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        Set<ConstraintViolation<User>> violations = validator.validate(result);
        assertThat(violations).hasSize(1);
    }

    @Test
    void dtoToUser_DniIsTooShort() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Usuario")
                .dni("123456")
                .status("ENABLED")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        Set<ConstraintViolation<User>> violations = validator.validate(result);
        assertThat(violations).hasSize(1);
    }

    @Test
    void dtoToUser_DniIsTooLong() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Usuario")
                .dni("123456789")
                .status("ENABLED")
                .credential(credDto)
                .build();

        User result = mapper.toEntity(userDto);

        Set<ConstraintViolation<User>> violations = validator.validate(result);
        assertThat(violations).hasSize(1);
    }

    @Test
    void dtoToUser_InvalidStatus_ThrowsException() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("PINDONGA")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Usuario")
                .dni("12345678")
                .status("ENABLED")
                .credential(credDto)
                .build();

        assertThrows(InvalidRequestException.class,() -> mapper.toEntity(userDto));
    }

    @Test
    void dtoToUser_UsernameContainsInvalidCharacters() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba!")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Usuario")
                .dni("12345678")
                .status("ENABLED")
                .credential(credDto)
                .build();

        assertThrows(ConstraintViolationException.class,() -> mapper.toEntity(userDto));
    }

    @Test
    void dtoToUser_UsernameIsTooShort() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("pr")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Usuario")
                .dni("12345678")
                .status("ENABLED")
                .credential(credDto)
                .build();

        assertThrows(ConstraintViolationException.class,() -> mapper.toEntity(userDto));
    }

    @Test
    void dtoToUser_UsernameIsTooLong() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("abcdefghijklmnopq")
                .password("12345678")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Usuario")
                .dni("12345678")
                .status("ENABLED")
                .credential(credDto)
                .build();

        assertThrows(ConstraintViolationException.class,() -> mapper.toEntity(userDto));
    }

    @Test
    void dtoToUser_PasswordIsTooShort() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("1234567")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Usuario")
                .dni("12345678")
                .status("ENABLED")
                .credential(credDto)
                .build();

        assertThrows(ConstraintViolationException.class,() -> mapper.toEntity(userDto));
    }

    @Test
    void dtoToUser_PasswordIsTooLong() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("ggu9wRGeeBPCH9D1KUXV9fKFFCO4qh9wag0FSSgVwS6v32CyEMedvhFFRQQALcgQM")
                .role("EMPLOYEE")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Usuario")
                .dni("12345678")
                .status("ENABLED")
                .credential(credDto)
                .build();

        assertThrows(ConstraintViolationException.class,() -> mapper.toEntity(userDto));
    }

    @Test
    void dtoToUser_InvalidRole_ThrowsException() {
        CreateCredentialDTO credDto = CreateCredentialDTO.builder()
                .username("prueba")
                .password("12345678")
                .role("AFILADOR")
                .build();

        CreateUserDTO userDto = CreateUserDTO.builder()
                .firstname("Prueba")
                .lastname("Usuario")
                .dni("12345678")
                .status("ENABLED")
                .credential(credDto)
                .build();

        assertThrows(InvalidRequestException.class,() -> mapper.toEntity(userDto));
    }
}