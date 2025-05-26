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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SupplierMapperTest {
    SupplierMapper mapper; //
    private Supplier supplier;
    private Address address;
    private Validator validator;

    @BeforeEach
    void setUp()
    {
        mapper = new SupplierMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void supplierToViewSupplierDTO_AllfieldsOK()
    {
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
    void supplierDTOToSupplierObject_AllfieldsOK()
    {
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
    void toObjectFromAddSupplierDTO_CompNameEmpty()
    {
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

}
