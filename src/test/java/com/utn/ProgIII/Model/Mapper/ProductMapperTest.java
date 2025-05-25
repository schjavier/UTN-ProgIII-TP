package com.utn.ProgIII.Model.Mapper;


import com.utn.ProgIII.dto.AddAddressDTO;
import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import com.utn.ProgIII.mapper.SupplierMapper;
import com.utn.ProgIII.model.Address.Address;
import com.utn.ProgIII.model.Supplier.Supplier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;
public class ProductMapperTest {
    SupplierMapper mapper = new SupplierMapper();


    @Test
    void toViewSupplierDTOTest()
    {
        Address address = new Address(
                "Test city",
                "123", // aca tampoco?
                "addd"
        );

        Supplier testSup = Supplier.builder()
                .idSupplier(1)
                .companyName("Test name")
                .cuit("23-12345678-9")
                .phoneNumber("546486945664")
                .email("test@gmail.com")
                .address(address).build();


        ViewSupplierDTO supplierDTO = mapper.toViewSupplierDTO(testSup);

        assertNotNull(supplierDTO);
    }

    @Test
    void toObjectFromAddDTOTest()
    {
        AddAddressDTO addAddressDTO = new AddAddressDTO(
                "Calle",
                "3123", // este string da algo como correcto??? 12as34
                "Ciudad"
        );

        AddSupplierDTO supplierDTO = new AddSupplierDTO(
                "Test name",
                "23-12345678-9",
                "546486945664",
                "test@gmail.com",
                addAddressDTO
        );


        Supplier supplier = mapper.toObjectFromAddSupplierDTO(supplierDTO);

        System.out.println(supplier);
        assertNotNull(supplier);
    }
}
