package com.utn.ProgIII.serviceTest;

import com.utn.ProgIII.dto.*;
import com.utn.ProgIII.exceptions.DuplicateEntityException;
import com.utn.ProgIII.exceptions.ProductNotFoundException;
import com.utn.ProgIII.exceptions.SupplierNotFoundException;
import com.utn.ProgIII.mapper.SupplierMapper;
import com.utn.ProgIII.model.Address.Address;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Supplier.Supplier;
import com.utn.ProgIII.repository.SupplierRepository;
import com.utn.ProgIII.service.implementations.SupplierServiceImpl;
import com.utn.ProgIII.validations.SupplierValidations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceTest {

    @Mock
    SupplierRepository supplierRepository;
    @Mock
    SupplierValidations supplierValidations;
    @Mock
    SupplierMapper suppliermapper;

    @InjectMocks
    SupplierServiceImpl supplierService;



    /* VALID SUPPLIER STRUCT */
    private static final Long SUPPLIER_ID = 1L;
    private static final String COMPANY_NAME = "Empresa 1";
    private static final String CUIT = "23-11111111-9";
    private static final String PHONE_NUMBER = "1111111111";
    private static final String EMAIL = "test1@email.com";
    private static final Long ADDRESS_ID = 1L;
    private static final String STREET = "Calle 12";
    private static final String NUMBER = "123";
    private static final String CITY = "Ciudad";

    private Supplier supplier;
    private AddSupplierDTO addSupplierDTO;
    private ViewSupplierDTO viewSupplierDTO;
    private ViewAddressDTO viewAddressDTO;

    /*OTHER DATA*/

    private static final Long INVALID_SUP_ID = 2L;

    Supplier createSupplier()
    {
        Supplier supplier = new Supplier();
        supplier.setIdSupplier(SUPPLIER_ID);
        supplier.setCompanyName(COMPANY_NAME);
        supplier.setCuit(CUIT);
        supplier.setPhoneNumber(PHONE_NUMBER);
        supplier.setEmail(EMAIL);
        supplier.setAddress(createAddress());

        return supplier;
    }

    Address createAddress()
    {
        Address address = new Address();
        address.setIdAddress(ADDRESS_ID);
        address.setNumber(NUMBER);
        address.setCity(CITY);
        address.setStreet(STREET);
        return address;
    }

    AddAddressDTO createAddAddressDTO()
    {
        return new AddAddressDTO(STREET,NUMBER,CITY);
    }

    AddSupplierDTO createAddSupplierDTO()
    {
        return new AddSupplierDTO(COMPANY_NAME,CUIT,PHONE_NUMBER,EMAIL, createAddAddressDTO());
    }

    ViewSupplierDTO createViewSupplierDTO()
    {
        return new ViewSupplierDTO(SUPPLIER_ID,COMPANY_NAME,CUIT,PHONE_NUMBER,EMAIL,createViewAddressDTO());
    }

    ViewAddressDTO createViewAddressDTO()
    {
        return new ViewAddressDTO(ADDRESS_ID,STREET,NUMBER,CITY);
    }

    @BeforeEach
    void setUp()
    {
        supplier = createSupplier();
        addSupplierDTO = createAddSupplierDTO();
        viewSupplierDTO = createViewSupplierDTO();
        viewAddressDTO = createViewAddressDTO();
    }


    @Test
    void deleteSupplier_ShouldThrowExceptionWhenNotFound()
    {
        assertThrows(SupplierNotFoundException.class,
                () -> supplierService.deleteSupplier(INVALID_SUP_ID));

        verify(supplierRepository, never()).save(any());
    }

    @Test
    void getSupplierById_ShouldReturnViewSupplierDTO_whenSupplierExists()
    {
        when(supplierRepository.findById(SUPPLIER_ID)).thenReturn(Optional.of(supplier));
        when(suppliermapper.toViewSupplierDTO(supplier)).thenReturn(viewSupplierDTO);

        ViewSupplierDTO result = supplierService.viewOneSupplier(SUPPLIER_ID);


        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.id(), SUPPLIER_ID);
        Assertions.assertEquals(result.companyName(), COMPANY_NAME);
        Assertions.assertEquals(result.cuit(),CUIT);
        Assertions.assertEquals(result.phoneNumber(),PHONE_NUMBER);
        Assertions.assertEquals(result.email(),EMAIL);
        Assertions.assertEquals(result.address(),viewAddressDTO);

        Mockito.verify(supplierRepository).findById(SUPPLIER_ID);
        Mockito.verify(suppliermapper).toViewSupplierDTO(supplier);
    }

    @Test
    void getSupplierById_ShouldThrowException_WhenSupplierDoesNotExist()
    {
        when(supplierRepository.findById(INVALID_SUP_ID)).thenReturn(Optional.empty());

        assertThrows(SupplierNotFoundException.class,
                () -> supplierService.viewOneSupplier(INVALID_SUP_ID));

        Mockito.verify(supplierRepository).findById(INVALID_SUP_ID);
    }

    @Test
    void updateSupplier_ShouldReturnUpdatedSupplier_whenValidInput()
    {
        Supplier inicial_supplier = createSupplier();
        inicial_supplier.getAddress().setStreet("Calle test");
        inicial_supplier.getAddress().setNumber("111");
        inicial_supplier.getAddress().setCity("ciudad");

        AddSupplierDTO updateDto = new AddSupplierDTO("Compania lolo","23-22222222-9","2222222222","test2@email.com",createAddAddressDTO());

        ViewSupplierDTO responseDto = new ViewSupplierDTO(1L,"Compania lolo","23-22222222-9","2222222222","test2@email.com",createViewAddressDTO());

        when(supplierRepository.findById(SUPPLIER_ID)).thenReturn(Optional.of(inicial_supplier));
        when(supplierRepository.save(any(Supplier.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        when(suppliermapper.toViewSupplierDTO(any(Supplier.class))).thenReturn(responseDto);

        ViewSupplierDTO result = supplierService.updateSupplier(updateDto,SUPPLIER_ID);

        assertEquals("Compania lolo", result.companyName());
        assertEquals("23-22222222-9", result.cuit());
        assertEquals("2222222222", result.phoneNumber());
        assertEquals("test2@email.com", result.email());
        assertEquals(createViewAddressDTO(),result.address());

        verify(supplierRepository).findById(SUPPLIER_ID);
        verify(supplierRepository).save(any(Supplier.class));
        ArgumentCaptor<Supplier> productArgumentCaptor = ArgumentCaptor.forClass(Supplier.class);
        verify(suppliermapper).toViewSupplierDTO(productArgumentCaptor.capture());
    }

    @Test
    void createSupplier_ShouldThrowAnException_whenRepeatSupplierName()
    {
        doThrow(new DuplicateEntityException("El proveedor con ese nombre ya existe en la base de datos"))
                .when(supplierValidations).validateCompanyNameNotExists(supplier.getCompanyName());

        assertThrows(DuplicateEntityException.class,
                () -> supplierService.createSupplier(addSupplierDTO));

        verify(supplierValidations).validateCompanyNameNotExists(supplier.getCompanyName());
        verify(supplierRepository, never()).save(any());
        verify(suppliermapper, never()).toViewSupplierDTO(any());
    }

    @Test
    void createSupplier_ShouldThrowAnException_whenRepeatSupplierCuit()
    {
        when(suppliermapper.toObjectFromAddSupplierDTO(addSupplierDTO)).thenReturn(supplier);

        doThrow(new DuplicateEntityException("El proveedor con ese nombre ya existe en la base de datos"))
                .when(supplierValidations).validateSupplierByCuit(supplier.getCuit());

        assertThrows(DuplicateEntityException.class,
                () -> supplierService.createSupplier(addSupplierDTO));

        verify(supplierValidations).validateSupplierByCuit(supplier.getCuit());
        verify(supplierRepository, never()).save(any());
        verify(suppliermapper, never()).toViewSupplierDTO(any());
    }
}
