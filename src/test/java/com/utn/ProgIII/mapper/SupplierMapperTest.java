package com.utn.ProgIII.mapper;


import com.utn.ProgIII.dto.AddAddressDTO;
import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import com.utn.ProgIII.model.Address.Address;
import com.utn.ProgIII.model.Supplier.Supplier;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SupplierMapperTest {
    SupplierMapper mapper; //
    private Supplier supplier;
    private Address address;
    private Validator validator;

    @BeforeEach
    void setUp() {
        mapper = new SupplierMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void supplierToViewSupplierDTO_AllfieldsOK() {
        address = new Address(
                "Test city",
                "123",
                "addd"
        );

        supplier = Supplier.builder()
                .idSupplier(1)
                .companyName("Test name")
                .cuit("23-12345678-9")
                .phoneNumber("546486945664")
                .email("test@gmail.com")
                .address(address).build();


        ViewSupplierDTO supplierDTO = mapper.toViewSupplierDTO(supplier);

        assertThat(supplierDTO)
                .hasNoNullFieldsOrProperties()
                .satisfies(result -> {
                    assertThat(result.companyName()).isEqualTo(supplier.getCompanyName());
                    assertThat(result.cuit()).isEqualTo(supplier.getCuit());
                    assertThat(result.phoneNumber()).isEqualTo(supplier.getPhoneNumber());
                    assertThat(result.email()).isEqualTo(supplier.getEmail());
                    assertThat(result.address().city()).isEqualTo(supplier.getAddress().getCity());
                    assertThat(result.address().number()).isEqualTo(supplier.getAddress().getNumber());
                    assertThat(result.address().street()).isEqualTo(supplier.getAddress().getStreet());
                });
    }

    @Test
    void supplierDTOToSupplierObject_AllfieldsOK() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("Calle")
                .number("123")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Compania")
                .cuit("23-12345678-9")
                .phoneNumber("1231546542")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        assertThat(addSupplierDTO)
                .hasNoNullFieldsOrProperties()
                .satisfies(result -> {
                    assertThat(result.companyName()).isEqualTo(supplier.getCompanyName());
                    assertThat(result.cuit()).isEqualTo(supplier.getCuit());
                    assertThat(result.phoneNumber()).isEqualTo(supplier.getPhoneNumber());
                    assertThat(result.email()).isEqualTo(supplier.getEmail());
                    assertThat(result.address().city()).isEqualTo(supplier.getAddress().getCity());
                    assertThat(result.address().number()).isEqualTo(supplier.getAddress().getNumber());
                    assertThat(result.address().street()).isEqualTo(supplier.getAddress().getStreet());
                });

    }


    @Test
    void toObjectFromAddSupplierDTO_CompNameEmpty() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("Calle")
                .number("123")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);
        assertThat(violations).hasSize(2);
    }

    @Test
    void toObjectFromAddSupplierDTO_CuitEmpty() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("Calle")
                .number("123")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);
        assertThat(violations).hasSize(2);
    }

    @Test
    void toObjectFromAddSupplierDTO_PhoneNumberEmpty() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("Calle")
                .number("123")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);
        assertThat(violations).hasSize(2);
    }

    @Test
    void toObjectFromSupplierDTO_PhoneNumberTooShort() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("Calle")
                .number("123")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("114435812")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);
        assertThat(violations).hasSize(1);
    }

    @Test
    void toObjectFromSupplierDTO_PhoneNumberTooLong() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("Calle")
                .number("123")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("11443582212")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);
        assertThat(violations).hasSize(1);
    }

    @Test
    void toObjectFromSupplierDTO_PhoneNumberHasLetter() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("Calle")
                .number("123")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("114435a82212")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);
        assertThat(violations).hasSize(1);
    }

    @Test
    void toObjectFromAddSupplierDTO_EmailEmpty() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("Calle")
                .number("123")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);
        assertThat(violations).hasSize(1);
    }

    @Test
    void toObjectFromSupplierDTO_EmailWrongFormat() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("Calle")
                .number("123")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("asd")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);
        assertThat(violations).hasSize(1);
    }

    @Test
    void toObjectFromAddSupplierDTO_AddressEmpty() {
        assertThrows(NullPointerException.class, () -> {
            /*AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                    .street("Calle")
                    .number("123")
                    .city("Ciudad").build();*/

            AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                    .companyName("Test")
                    .cuit("23-12345678-9")
                    .phoneNumber("1144358129")
                    .email("")
                    .build();

            Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);
        });
    }

    @Test
    void toObjectFromAddSupplierDTO_StreetEmpty() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("")
                .number("123")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Address>> violations = validator.validate(supplier.getAddress());
        assertThat(violations).hasSize(3);
    }

    @Test
    void toObjectFromAddSupplierDTO_StreetTooShort() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("a")
                .number("123")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Address>> violations = validator.validate(supplier.getAddress());
        assertThat(violations).hasSize(1);
    }

    @Test
    void toObjectFromAddSupplierDTO_StreetTooLong() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("aasdasdasdasdaswdeawrascasczxcasdasdasdasdasdasdasdasdasdaswdeawrascasczxcasdasdasdasdasdasdasdasdasd")
                .number("123")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Address>> violations = validator.validate(supplier.getAddress());
        assertThat(violations).hasSize(1);
    }

    @Test
    void toObjectFromAddSupplierDTO_StreetIncorrectFormat() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("!!!")
                .number("123")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Address>> violations = validator.validate(supplier.getAddress());
        assertThat(violations).hasSize(1);
    }


    @Test
    void toObjectFromAddSupplierDTO_NumberIsEmpty() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("asdasd")
                .number("")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Address>> violations = validator.validate(supplier.getAddress());
        assertThat(violations).hasSize(3);
    }

    @Test
    void toObjectFromAddSupplierDTO_NumberIsTooShort() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("asdasd")
                .number("5")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Address>> violations = validator.validate(supplier.getAddress());
        assertThat(violations).hasSize(1);
    }

    @Test
    void toObjectFromAddSupplierDTO_NumberIsTooLong() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("asdasd")
                .number("5456445")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Address>> violations = validator.validate(supplier.getAddress());
        assertThat(violations).hasSize(1);
    }

    @Test
    void toObjectFromAddSupplierDTO_NumberContainsNonDigit() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("asdasd")
                .number("12a3")
                .city("Ciudad").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Address>> violations = validator.validate(supplier.getAddress());
        assertThat(violations).hasSize(1);
    }


    @Test
    void toObjectFromSupplierDTO_CityIsEmpty() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("asdasd")
                .number("123")
                .city("").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Address>> violations = validator.validate(supplier.getAddress());
        assertThat(violations).hasSize(3);
    }

    @Test
    void toObjectFromSupplierDTO_CityIsTooShort() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("asdasd")
                .number("123")
                .city("a").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Address>> violations = validator.validate(supplier.getAddress());
        assertThat(violations).hasSize(1);
    }

    @Test
    void toObjectFromSupplierDTO_CityIsTooLong() {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("asdasd")
                .number("123")
                .city("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Address>> violations = validator.validate(supplier.getAddress());
        assertThat(violations).hasSize(1);
    }

    @Test
    void toObjectFromSupplierDTO_CityIsIncorrectlyFormatted()
    {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("asdasd")
                .number("123")
                .city("!!!!!").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Address>> violations = validator.validate(supplier.getAddress());
        assertThat(violations).hasSize(1);
    }

    @Test
    void toObjectFromSupplierDTO_CityIsIncorrectlyAndTooShort()
    {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("asdasd")
                .number("123")
                .city("!").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Address>> violations = validator.validate(supplier.getAddress());
        assertThat(violations).hasSize(2);
    }

    @Test
    void toObjectFromSupplierDTO_CityIsIncorrectlyAndTooLong()
    {
        AddAddressDTO addAddressDTO = AddAddressDTO.builder()
                .street("asdasd")
                .number("123")
                .city("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!").build();

        AddSupplierDTO addSupplierDTO = AddSupplierDTO.builder()
                .companyName("Test")
                .cuit("23-12345678-9")
                .phoneNumber("1144358129")
                .email("email@gmail.com")
                .address(addAddressDTO).build();

        Supplier supplier = mapper.toObjectFromAddSupplierDTO(addSupplierDTO);

        Set<ConstraintViolation<Address>> violations = validator.validate(supplier.getAddress());
        assertThat(violations).hasSize(2);
    }



}
