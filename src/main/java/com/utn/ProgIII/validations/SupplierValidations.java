package com.utn.ProgIII.validations;

import com.utn.ProgIII.exceptions.DuplicateUserException;
import com.utn.ProgIII.repository.SupplierRepository;
import org.springframework.stereotype.Component;

@Component
public class SupplierValidations {
    SupplierRepository supplierRepository;

    public SupplierValidations(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public void validateSupplierByCuit(String cuit) {
        if(supplierRepository.existsByCuit(cuit)) {
            throw new DuplicateUserException("El CUIT ingresado ya se encuentra registrado");
        }
    }

    public void validateModifiedSupplierByCuit(String currentCuit, String newCuit){
        if(supplierRepository.existsByCuit(newCuit) && !currentCuit.equals(newCuit)){
            throw new DuplicateUserException("El CUIT ingresado ya se encuentra registrado");
        }
    }
}
