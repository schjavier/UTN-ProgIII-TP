package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.model.ProductSupplier.dto.CreateProductSupplierDTO;
import com.utn.ProgIII.model.ProductSupplier.dto.ResponseProductSupplierDTO;
import com.utn.ProgIII.model.ProductSupplier.dto.SupplierProductListDTO;
import com.utn.ProgIII.model.ProductSupplier.dto.UpdateProductSupplierDTO;

public interface ProductSupplierService {

    ResponseProductSupplierDTO createProductSupplier(CreateProductSupplierDTO createProductSupplierDTO);
    ResponseProductSupplierDTO updateProductSupplier(UpdateProductSupplierDTO updateProductSupplierDTO, Long id);

    SupplierProductListDTO listProductsBySupplier(String companyName);



}
