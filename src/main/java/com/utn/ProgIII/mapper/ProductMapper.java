package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.exceptions.ProductNotFoundException;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import org.springframework.stereotype.Component;

import static java.util.prefs.Preferences.MAX_NAME_LENGTH;

@Component
public class ProductMapper {


    public ProductDTO toProductDTO (Product product) {
        Long idProduct = product.getIdProduct();
        String name = product.getName();
        String status = product.getStatus().toString();

        return new ProductDTO(idProduct,name,status);
    }


    public Product toEntity (ProductDTO productDTO){
        validateProduct(productDTO);

        Product result = new Product();

        result.setName(productDTO.name());
        result.setStatus(productDTO.status().isBlank() ? ProductStatus.ENABLED : ProductStatus.valueOf(productDTO.status().toUpperCase()));

        return result;
    }


    private void validateProduct(ProductDTO productDto) {
        if (productDto.name() == null) {
            throw new ProductNotFoundException("El nombre del producto no puede ser nulo");
        }

        String nombre = productDto.name().trim();
        if (nombre.length() < 3) {
            throw new ProductNotFoundException("El nombre del producto debe tener al menos 3 caracteres");
        }

        if (nombre.length() > MAX_NAME_LENGTH) {
            throw new ProductNotFoundException("El nombre del producto no puede exceder los " + MAX_NAME_LENGTH + " caracteres");
        }
    }


}
