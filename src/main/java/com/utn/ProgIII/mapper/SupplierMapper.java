package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import com.utn.ProgIII.model.Address.Address;
import com.utn.ProgIII.model.Supplier.Supplier;
import org.springframework.stereotype.Component;

/**
 * Convierte Objetos de Java a objetos DTO y viceversa
 */
@Component
public class SupplierMapper {


    /**
     * Hace un DTO para usar en visualizar datos
     * @param supplier Objeto Java Supplier
     * @return Un objeto DTO
     */
    public ViewSupplierDTO toViewSupplierDTO(Supplier supplier)
    {
        return new ViewSupplierDTO(
                supplier.getIdSupplier(),
                supplier.getCompanyName(),
                supplier.getCuit(),
                supplier.getPhoneNumber(),
                supplier.getEmail(),
                supplier.getAddress()
        );
    }

    /**
     * Hace un objeto java para cargar datos a la DB
     * @param supplierDTO Un objeto AddSupplierDTO
     * @return
     */
    public Supplier toObjectFromAddSupplierDTO(AddSupplierDTO supplierDTO) {
        Supplier sup = new Supplier();

        sup.setCompanyName(supplierDTO.companyName());
        sup.setCuit(supplierDTO.cuit());
        sup.setPhoneNumber(supplierDTO.phoneNumber());
        sup.setEmail(supplierDTO.email());
        sup.setAddress(supplierDTO.address());

        return sup;
    }

}
