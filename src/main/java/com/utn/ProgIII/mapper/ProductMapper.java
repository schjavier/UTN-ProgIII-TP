package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.exceptions.InvalidRequestException;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import org.apache.commons.lang3.EnumUtils;
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

        if(productDTO.status() != null && !EnumUtils.isValidEnum(ProductStatus.class, productDTO.status().toUpperCase()))
        {
            throw new InvalidRequestException("El estado no es valido");
        }

        result.setStatus(productDTO.status() == null ? ProductStatus.ENABLED : ProductStatus.valueOf(productDTO.status().toUpperCase()));

        return result;
    }
}
