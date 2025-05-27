package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.exceptions.ProductNotFoundException;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {


    public ProductDTO toProductDTO (Product product) {
        Long idProduct = product.getIdProduct();
        String name = product.getName();
        String status = product.getStatus().toString();

        return new ProductDTO(idProduct,name,status);
    }


    public Product toEntity (ProductDTO productDTO){
        validarProducto(productDTO);

        Product result = new Product();

        result.setName(productDTO.name());
        result.setStatus(productDTO.status().isBlank() ? ProductStatus.ENABLED : ProductStatus.valueOf(productDTO.status().toUpperCase()));

        return result;
    }

    private void validarProducto(ProductDTO productDTO) {
        if (productDTO.name() == null || productDTO.name().trim().length() < 3) {
            throw new ProductNotFoundException("El nombre del producto debe tener al menos 3 caracteres");
        }
    }

    private void validarProducto(ProductDTO productDto) {
        if (productDto.getName() == null) {
            throw new IllegalArgumentException("El nombre del producto no puede ser nulo");
        }

        String nombre = productDto.getName().trim();
        if (nombre.length() < 3) {
            throw new IllegalArgumentException("El nombre del producto debe tener al menos 3 caracteres");
        }

        if (nombre.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("El nombre del producto no puede exceder los " + MAX_NAME_LENGTH + " caracteres");
        }
    }


}
