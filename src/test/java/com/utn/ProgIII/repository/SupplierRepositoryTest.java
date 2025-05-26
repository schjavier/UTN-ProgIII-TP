package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.Address.Address;
import com.utn.ProgIII.model.Supplier.Supplier;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SupplierRepositoryTest {

    @Autowired
    private SupplierRepository supplierRepository;


    @Test
    public void saveSupplierTest()
    {
        Address address = new Address(
                "Test city",
                "123",
                "addd"
        );

        Supplier testSup = Supplier.builder()
                    .companyName("Test name")
                    .cuit("23-12345678-9")
                    .phoneNumber("546486945664")
                    .email("test@gmail.com")
                    .address(address).build();


        Supplier saved_supplier = supplierRepository.save(testSup);

        assertNotNull(saved_supplier);
        assertEquals(saved_supplier.getCompanyName(), "Test name");
    }

    @Test
    public void getSupplierByIdTest()
    {
        Supplier supplier = supplierRepository.findById(1L).get();

        Assertions.assertThat(supplier.getIdSupplier()).isEqualTo(1L);
    }

    @Test
    public void getListOfSuppliers()
    {
        List<Supplier> suppliers = supplierRepository.findAll();

        Assertions.assertThat(suppliers.size()).isGreaterThan(0);
    }

    @Test
    public void updateSupplier_fail()
    {
        assertThrows(NoSuchElementException.class,() -> {
            Supplier supplier = supplierRepository.findById(1L).get();
        });
    }

    @Test
    public void deleteSupplierTest()
    {
        supplierRepository.deleteById(1L);


        Supplier supplier = null;
        Optional<Supplier> optionalSupplier = supplierRepository.findById(1L);

        if(optionalSupplier.isPresent())
        {
            supplier = optionalSupplier.get();
        }

        Assertions.assertThat(supplier).isNull();

    }

    @Test
    public void existsByQuitTest()
    {
        Address address = new Address(
                "Test city",
                "123",
                "addd"
        );

        Supplier testSup = Supplier.builder()
                .companyName("Test name")
                .cuit("23-88884444-9")
                .phoneNumber("546486945664")
                .email("test@gmail.com")
                .address(address).build();


        Supplier saved_supplier = supplierRepository.save(testSup);



        boolean exists = supplierRepository.existsByCuit(saved_supplier.getCuit());


        Assertions.assertThat(exists).isEqualTo(true);
    }

    @Test
    public void searchByCompanyName() // no deberiamos tener una lista de companias con el mismo nombre
                                    // o tenemos que tener una verificacion para que cada compania tenga su nombre
    {
        String test_name = "COMPTEST";

        Address address = new Address(
                "Test city",
                "123",
                "addd"
        );

        Supplier testSup = Supplier.builder()
                .companyName(test_name)
                .cuit("23-88884444-9")
                .phoneNumber("546486945664")
                .email("test@gmail.com")
                .address(address).build();


        Supplier saved_supplier = supplierRepository.save(testSup);

        Optional<Supplier> supplier_op = supplierRepository.findByCompanyName(test_name);
        Supplier found_supplier = null;

        if(supplier_op.isPresent())
        {
            found_supplier = supplier_op.get();
        }

        assertNotNull(found_supplier);
        Assertions.assertThat(found_supplier.getCompanyName()).isEqualTo(test_name);
    }

}
