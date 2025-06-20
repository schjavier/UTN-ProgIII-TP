package com.utn.ProgIII.validations;

import com.utn.ProgIII.exceptions.DuplicateEntityException;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductValidations {
    @Autowired
    private ProductRepository productRepository;


    /**
     * Se verifica si un producto con ese nombre existe
     * @param product
     */
    public void validateProductNameExists(Product product)
    {
        if(productRepository.existsByName(product.getName()))
        {
            throw new DuplicateEntityException("Ese producto ya existe");
        }
    }


}
