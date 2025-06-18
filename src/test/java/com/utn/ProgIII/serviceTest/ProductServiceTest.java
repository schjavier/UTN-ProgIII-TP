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

import java.util.Optional;

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
    private static final ProductStatus STATUS = ProductStatus.ENABLED;
    private static final Long NON_EXISTING_ID = 2L;

    private Product productMock;
    private ProductDTO productDTOMock;

    @BeforeEach
    void setUp(){

        productMock = new Product();
        productMock.setIdProduct(PRODUCT_ID);
        productDTOMock = new ProductDTO(PRODUCT_ID, PRODUCT_NAME, STATUS.toString());

    }

    @Test
    public void getProductById_shouldReturnProductDTO_whenProductExists(){

        Mockito.when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(productMock));
        Mockito.when(productMapper.toProductDTO(productMock)).thenReturn(productDTOMock);

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

        Mockito.when(productRepository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(NON_EXISTING_ID));

        Mockito.verify(productRepository).findById(NON_EXISTING_ID);
    }


    public void getUserBy_shouldThrowException_whenIfCondition(){


    }

}
