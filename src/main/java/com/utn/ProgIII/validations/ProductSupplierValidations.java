package com.utn.ProgIII.validations;

import com.utn.ProgIII.exceptions.DuplicateRelationshipException;
import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import com.utn.ProgIII.repository.ProductSupplierRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductSupplierValidations {

    private final ProductSupplierRepository productSupplierRepository;

    public ProductSupplierValidations(ProductSupplierRepository productSupplierRepository){
        this.productSupplierRepository = productSupplierRepository;
    }

    public void validateRelationship(ProductSupplier productSupplier){
        if (productSupplierRepository.existsByProductAndSupplier(productSupplier.getProduct(),productSupplier.getSupplier())){
            throw new DuplicateRelationshipException("La relación del proveedor con el producto ya se encuentra registrada");
        }
    }


}
