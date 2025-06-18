package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

// para la capa de servicio
public interface SupplierService {
    ViewSupplierDTO viewOneSupplier(long id);
    void deleteSupplier(long id);
    ViewSupplierDTO updateSupplier(AddSupplierDTO supplierDTO, Long id);
    ViewSupplierDTO createSupplier(AddSupplierDTO supplierDTO);
    Page<ViewSupplierDTO> listSuppliers(Pageable pageable);
    List<ViewSupplierDTO> getAllSuppliers();


}
