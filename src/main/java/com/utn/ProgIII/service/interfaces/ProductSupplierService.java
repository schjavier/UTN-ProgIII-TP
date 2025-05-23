package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.model.ProductSupplier.CreateProductSupplierDTO;
import com.utn.ProgIII.model.ProductSupplier.DatosProductSupplierDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSupplierService {

    DatosProductSupplierDTO createProductSupplier(CreateProductSupplierDTO createProductSupplierDTO);
    DatosProductSupplierDTO getOneProductSupplier(Long id);
    Page<DatosProductSupplierDTO> getAllProductsSupplier(Pageable pagination);
    boolean deleteProductSupplier(Long id);

}
