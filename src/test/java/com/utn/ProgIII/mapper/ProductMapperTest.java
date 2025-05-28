package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.exceptions.ProductNotFoundException;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {
    private ProductMapper productMapper;
    private Product product;
    private Validator validator;


    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void productToDTO() {
        product = Product.builder()
                .idProduct(1L)
                .name("product")
                .status(ProductStatus.ENABLED)
                .build();

        ProductDTO productDTO = productMapper.toProductDTO(product);

        assertThat(productDTO)
                .hasNoNullFieldsOrProperties()
                .satisfies( result -> {
                    assertThat(result.idProduct().equals(product.getIdProduct()));
                    assertThat(result.name().equals(product.getName()));
                    assertThat(result.status().equals(product.getStatus().toString()));
                });
    }

    @Test
    void dtoToProduct() {
        ProductDTO productDTO = ProductDTO.builder()
                .name ("product")
                .status("ENABLED")
                .build();

        Product result = productMapper.toEntity(productDTO);

        assertThat(result)
                .hasNoNullFieldsOrPropertiesExcept("idProduct", "productSuppliers")
                .satisfies(mappedEntity ->{

                    assertThat(mappedEntity.getName().equals((productDTO.name())));
                    assertThat(mappedEntity.getStatus().equals(ProductStatus.ENABLED));
                    
                });
    }

    @Test
    public void nameNull() {

        ProductDTO productDTO = ProductDTO.builder()
                .name(null)
                .build();

        assertThrows(ProductNotFoundException.class, () -> {
            productMapper.toEntity(productDTO);
        });

    }

    @Test
    void name2Characters (){
        ProductDTO productDTO = ProductDTO.builder()

                // Exactamente 2 caracteres
                .name("ab")
                .build();

        assertThrows(ProductNotFoundException.class,  () -> {
            Product product = productMapper.toEntity(productDTO);
        });
    }

    @Test
    public void name51characters() {
        ProductDTO productDTO = ProductDTO.builder()

                // Exactamente 51 caracteres
                .name("a".repeat(51))
                .build();

        assertThrows(ProductNotFoundException.class, () -> {
            productMapper.toEntity(productDTO);
        });
    }

}