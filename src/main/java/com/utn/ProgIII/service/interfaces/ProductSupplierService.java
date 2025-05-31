package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.CreateProductSupplierDTO;
import com.utn.ProgIII.dto.ResponseProductSupplierDTO;
import com.utn.ProgIII.dto.SupplierProductListDTO;
import com.utn.ProgIII.dto.UpdateProductSupplierDTO;

public interface ProductSupplierService {

    ResponseProductSupplierDTO createProductSupplier(CreateProductSupplierDTO createProductSupplierDTO);
    ResponseProductSupplierDTO updateProductSupplier(UpdateProductSupplierDTO updateProductSupplierDTO, Long id);
    SupplierProductListDTO listProductsBySupplier(String companyName);
    String uploadCsv(String filepath, Long idSupplier);
}
