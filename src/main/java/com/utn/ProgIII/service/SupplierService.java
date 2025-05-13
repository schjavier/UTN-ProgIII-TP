package com.utn.ProgIII.service;

import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.dto.ViewSupplierDTO;

import java.util.List;

// para la capa de servicio
public interface SupplierService {
    ViewSupplierDTO viewOneSupplier(long id);
    boolean deleteSupplier(long id);
    ViewSupplierDTO modifySupplier(ViewSupplierDTO supplierDTO,long id);
    ViewSupplierDTO createSupplier(AddSupplierDTO supplierDTO);
    List<ViewSupplierDTO> listSuppliers(int page, int size);



}
