package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.dto.ViewAddressDTO;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import com.utn.ProgIII.exceptions.NullAddressException;
import com.utn.ProgIII.model.Address.Address;
import com.utn.ProgIII.model.Supplier.Supplier;
import org.springframework.stereotype.Component;

/**
 * Convierte objetos de Java a objetos DTO y viceversa
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
                new ViewAddressDTO(
                        supplier.getIdSupplier(),
                        supplier.getAddress().getStreet(),
                        supplier.getAddress().getNumber(),
                        supplier.getAddress().getCity()
                )
        );
    }

    /**
     * Hace un objeto java para cargar datos a la DB
     * @param supplierDTO Un objeto AddSupplierDTO
     * @return un Objeto Java de proveedor
     */
    public Supplier toObjectFromAddSupplierDTO(AddSupplierDTO supplierDTO) {
        Supplier sup = new Supplier();
        Address address = new Address();

        if(supplierDTO.address() == null)
        {
            throw new NullAddressException("La dirección está faltante.");
        }

        sup.setCompanyName(supplierDTO.companyName());
        sup.setCuit(supplierDTO.cuit());
        sup.setPhoneNumber(supplierDTO.phoneNumber());
        sup.setEmail(supplierDTO.email());

        address.setStreet(supplierDTO.address().street());
        address.setNumber(supplierDTO.address().number());
        address.setCity(supplierDTO.address().city());
        sup.setAddress(address);

        return sup;
    }

    /**
     * Hace un objeto java para cargar datos a la DB
     * @param supplierDTO Un Objeto de SupplierDTO
     * @return un Objeto Java de proveedor
     */
    public Supplier toObjectFromViewSupplierDTO(ViewSupplierDTO supplierDTO)
    {
        Supplier sup = new Supplier();
        Address address = new Address();



        sup.setIdSupplier(supplierDTO.id());
        sup.setCompanyName(supplierDTO.companyName());
        sup.setCuit(supplierDTO.cuit());
        sup.setPhoneNumber(supplierDTO.phoneNumber());
        sup.setEmail(supplierDTO.email());

        if(supplierDTO.address() == null)
        {
            throw new NullAddressException("La dirección está faltante.");
        }

        address.setIdAddress(supplierDTO.address().idaddress());
        address.setStreet(supplierDTO.address().street());
        address.setNumber(supplierDTO.address().number());
        address.setCity(supplierDTO.address().city());
        sup.setAddress(address);

        return sup;
    }

}
