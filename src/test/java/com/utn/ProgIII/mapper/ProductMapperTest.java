package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.exceptions.ProductNotFoundException;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {
    private ProductMapper productMapper;
    private Product product;


    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
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
                .hasNoNullFieldsOrPropertiesExcept()
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

        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productMapper.toEntity(productDTO);
        });

        assertEquals("El nombre del producto debe tener al menos 3 caracteres", exception.getMessage());
    }

    @Test
    public void name2Characters() {

        ProductDTO productDTO = ProductDTO.builder()

                // 2 caracteres
                .name("ab")
                .build();

        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productMapper.toEntity(productDTO);
        });

        assertEquals("El nombre del producto debe tener al menos 3 caracteres", exception.getMessage());
    }


    @Test
    public void name3characters() {
        ProductDTO productDTO = ProductDTO.builder()

                // Exactamente 3 caracteres
                .name("abc")
                .status("ENABLE")     //esto tambien se incluye?
                .build();

        assertDoesNotThrow(() -> {
            productMapper.toEntity(productDTO);
        });
    }

    @Test
    public void nameMoreThan3Characters() {
        ProductDTO productDTO = ProductDTO.builder()

                // Más de 3 caracteres
                .name("mas de tres")
                .status("ENABLE")    // esto tambien se incluye?
                .build();

        assertDoesNotThrow(() -> {
            productMapper.toEntity(productDTO);
        });
    }

    @Test
    public void name50characters() {
        ProductDTO productDTO = ProductDTO.builder()

                // Exactamente 50 caracteres
                .name("a".repeat(50))
                .status("ENABLE")    // esto tambien se incluye?
                .build();

        assertDoesNotThrow(() -> {
            productMapper.toEntity(productDTO);
        });
    }

    @Test
    public void cuandoNombreTiene51Caracteres_debeLanzarExcepcion() {
        ProductDTO productDTO = ProductDTO.builder()

                // Mas de 50 caracteres
                .name("a".repeat(51))
                .build();


        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productMapper.toEntity(productDTO);
        });

        assertEquals("El nombre del producto no puede exceder los 50 caracteres", exception.getMessage());
    }


}