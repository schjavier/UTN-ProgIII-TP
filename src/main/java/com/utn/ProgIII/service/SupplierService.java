package com.utn.ProgIII.service;

import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.model.Supplier.Supplier;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import org.springframework.data.domain.Page;

// para la capa de servicio
public interface SupplierService {
    ViewSupplierDTO viewOneSupplier(long id);
    boolean deleteSupplier(long id);
    ViewSupplierDTO modifySupplier(ViewSupplierDTO supplierDTO,long id);
    AddSupplierDTO createSupplier(AddSupplierDTO supplierDTO);
    Page<Supplier> listSuppliers(int page, int size);



}
