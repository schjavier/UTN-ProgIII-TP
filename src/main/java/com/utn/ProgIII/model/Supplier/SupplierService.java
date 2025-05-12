package com.utn.ProgIII.model.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// para la capa de servicio
public interface SupplierService {
    ViewSupplierDTO viewOneSupplier(long id);
    boolean deleteSupplier(long id);
    ViewSupplierDTO modifySupplier(ViewSupplierDTO supplierDTO,long id);
    AddSupplierDTO createSupplier(AddSupplierDTO supplierDTO);
    Page<Supplier> listSuppliers(int page, int size);



}
