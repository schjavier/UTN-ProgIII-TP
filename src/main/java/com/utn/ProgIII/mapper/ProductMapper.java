package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.ProductDTO;
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
        Product result = new Product();

        result.setName(productDTO.name());
        result.setStatus(productDTO.status().isBlank() ? ProductStatus.ENABLED : ProductStatus.valueOf(productDTO.status().toUpperCase()));

        return result;
    }


}
