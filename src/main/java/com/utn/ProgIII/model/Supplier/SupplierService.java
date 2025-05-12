package com.utn.ProgIII.model.Supplier;

import org.springframework.data.domain.Page;
// para la capa de servicio
public interface SupplierService {
    ViewSupplierDTO viewOneSupplier(long id);
    boolean deleteSupplier(long id);
    ViewSupplierDTO modifySupplier(ViewSupplierDTO supplierDTO,long id);
    AddSupplierDTO createSupplier();
    Page<ViewSupplierDTO> listSuppliers();

}
