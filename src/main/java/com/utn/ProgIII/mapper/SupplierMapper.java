package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.dto.ViewAddressDTO;
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
    public ViewSupplierDTO toViewSupplierDTO(Supplier supplier) // fix this
    {
        System.out.println(supplier.toString());
        return new ViewSupplierDTO(
                supplier.getIdsupplier(),
                supplier.getCompanyname(),
                supplier.getCuit(),
                supplier.getPhonenumber(),
                supplier.getEmail(),
                new ViewAddressDTO(supplier.getAddress().getIdaddress(),supplier.getAddress().getStreet(),supplier.getAddress().getNumber(),supplier.getAddress().getCity())
        );
    }

    /**
     * Hace un objeto java para cargar datos a la DB
     * @param supplierDTO Un objeto AddSupplierDTO
     * @return
     */
    public Supplier toObjectFromAddSupplierDTO(AddSupplierDTO supplierDTO) {
        Supplier sup = new Supplier();
        Address address = new Address();
        sup.setCompanyname(supplierDTO.companyname());
        sup.setCuit(supplierDTO.cuit());
        sup.setPhonenumber(supplierDTO.phonenumber());
        sup.setEmail(supplierDTO.email());

        address.setStreet(supplierDTO.address().street());
        address.setNumber(supplierDTO.address().number());
        address.setCity(supplierDTO.address().city());
        sup.setAddress(address);

        return sup;
    }

}
