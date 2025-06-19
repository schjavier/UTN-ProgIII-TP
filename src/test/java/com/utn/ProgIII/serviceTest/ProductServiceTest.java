package com.utn.ProgIII.serviceTest;

import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.exceptions.ProductNotFoundException;
import com.utn.ProgIII.mapper.ProductMapper;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import com.utn.ProgIII.repository.ProductRepository;
import com.utn.ProgIII.repository.ProductSupplierRepository;
import com.utn.ProgIII.service.implementations.AuthServiceImpl;
import com.utn.ProgIII.service.implementations.ProductServiceImpl;
import com.utn.ProgIII.service.interfaces.AuthService;
import com.utn.ProgIII.service.interfaces.ProductService;
import com.utn.ProgIII.validations.ProductValidations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;
    @Mock
    ProductMapper productMapper;
    @Mock
    ProductSupplierRepository productSupplierRepository;
    @Mock
    AuthService authService;
    @Mock
    ProductValidations productValidations;

    @InjectMocks
    ProductServiceImpl productService;

    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_NAME = "producto1";
    private static final Long PRODUCT2_ID = 3L;
    private static final String PRODUCT2_NAME = "producto1";
    private static final ProductStatus STATUS = ProductStatus.ENABLED;
    private static final ProductStatus STATUS_DISABLED = ProductStatus.DISABLED;

    private static final Long NON_EXISTING_ID = 2L;

    private Product productMock;
    private Product disabledProductMock;
    private ProductDTO productDTOMock;
    private ProductDTO productDTOMock2;
    private List<Product> productList;
    private List<Product> onlyEnabledProductList;

    @BeforeEach
    void setUp(){

        productMock = new Product();
        productMock.setIdProduct(PRODUCT_ID);

        disabledProductMock = new Product();
        disabledProductMock.setIdProduct(PRODUCT_ID);
        disabledProductMock.setStatus(STATUS_DISABLED);

        productDTOMock = new ProductDTO(PRODUCT_ID, PRODUCT_NAME, STATUS.toString());
        productDTOMock2 = new ProductDTO(PRODUCT2_ID, PRODUCT2_NAME, STATUS_DISABLED.toString());

        productList = new ArrayList<>();
        productList.add(productMock);
        productList.add(disabledProductMock);

        onlyEnabledProductList = new ArrayList<>();
        onlyEnabledProductList.add(productMock);


    }

    @Test
    public void getProductById_shouldReturnProductDTO_whenProductExists(){

        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(productMock));
        when(productMapper.toProductDTO(productMock)).thenReturn(productDTOMock);

        ProductDTO result = productService.getProductById(PRODUCT_ID);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.idProduct(), PRODUCT_ID);
        Assertions.assertEquals(result.name(), PRODUCT_NAME);
        Assertions.assertEquals(result.status(), STATUS.toString());

        Mockito.verify(productRepository).findById(PRODUCT_ID);
        Mockito.verify(productMapper).toProductDTO(productMock);

    }

    @Test
    public void getUserById_shouldThrowException_whenProductNotExists(){

        when(productRepository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(NON_EXISTING_ID));

        Mockito.verify(productRepository).findById(NON_EXISTING_ID);
    }

    @Test
    public void getUserBy_shouldThrowException_whenUserIsEmployeeAndProductIsDisabled(){

        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(disabledProductMock));
        when(authService.isEmployee("ROLE_EMPLOYEE")).thenReturn(true);

        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(PRODUCT_ID));

        verify(productRepository).findById(PRODUCT_ID);
        verify(authService).isEmployee("ROLE_EMPLOYEE");
        verify(productMapper, never()).toProductDTO(any());
    }


    @Test
    void getUserById_shouldReturnProduct_whenEmployeeAndProductEnabled(){
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(productMock));
        when(authService.isEmployee("ROLE_EMPLOYEE")).thenReturn(true);
        when(productMapper.toProductDTO(productMock)).thenReturn(productDTOMock);

        ProductDTO result = productService.getProductById(PRODUCT_ID);

        assertEquals(productDTOMock, result);
        verify(productMapper).toProductDTO(productMock);

    }

    @Test
    void getAllProducts_shouldReturnListOfAllProducts_whenUserManager(){

        when(authService.hasRole("ROLE_MANAGER")).thenReturn(true);
        when(productRepository.findAll()).thenAnswer(
                invocation -> productList
        );
        when(productMapper.toProductDTO(productMock)).thenReturn(productDTOMock);
        when(productMapper.toProductDTO(disabledProductMock)).thenReturn(productDTOMock2);

        List<ProductDTO> result = productService.getAllProduct();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(productDTOMock, productDTOMock2)));
        verify(productRepository).findAll();
        verify(productRepository, never()).findByStatus(any());
    }

    @Test
    void getAllProducts_shouldReturnOnlyEnabledProducts_whenUserEmployee(){

        when(authService.hasRole("ROLE_MANAGER")).thenReturn(false);
        when(authService.hasRole("ROLE_EMPLOYEE")).thenReturn(true);
        when(productRepository.findByStatus(STATUS)).thenAnswer(
                invocation -> onlyEnabledProductList
        );

        when(productMapper.toProductDTO(productMock)).thenReturn(productDTOMock);

        List<ProductDTO> result = productService.getAllProduct();

        assertEquals(1, result.size());
        assertTrue(result.contains(productDTOMock));
        verify(productRepository).findByStatus(STATUS);


    }

}
