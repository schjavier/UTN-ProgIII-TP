package com.utn.ProgIII.validations;

import com.utn.ProgIII.exceptions.DuplicateEntityException;
import com.utn.ProgIII.repository.SupplierRepository;
import org.springframework.stereotype.Component;

@Component
public class SupplierValidations {
    SupplierRepository supplierRepository;

    public SupplierValidations(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public void validateCompanyNameNotExists(String companyName) {
        if(supplierRepository.existsByCompanyName(companyName)) {
            throw new DuplicateEntityException("El proveedor con ese nombre ya existe en la base de datos");
        }
    }

    public void validateModifiedCompanyNameNotExists(String currentCompanyName, String newCompanyName) {
        if(supplierRepository.existsByCompanyName(newCompanyName) && !newCompanyName.equals(currentCompanyName)) {
            throw new DuplicateEntityException("El proveedor con ese nombre ya existe en la base de datos");
        }
    }

    public void validateSupplierByCuit(String cuit) {
        if(supplierRepository.existsByCuit(cuit)) {
            throw new DuplicateEntityException("El CUIT ingresado ya se encuentra registrado");
        }
    }

    public void validateModifiedSupplierByCuit(String currentCuit, String newCuit){
        if(supplierRepository.existsByCuit(newCuit) && !currentCuit.equals(newCuit)){
            throw new DuplicateEntityException("El CUIT ingresado ya se encuentra registrado");
        }
    }
}
