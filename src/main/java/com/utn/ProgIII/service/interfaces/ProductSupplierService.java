package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.model.ProductSupplier.CreateProductSupplierDTO;
import com.utn.ProgIII.model.ProductSupplier.ResponseProductSupplierDTO;
import com.utn.ProgIII.model.ProductSupplier.SupplierProductListDTO;
import com.utn.ProgIII.model.ProductSupplier.UpdateProductSupplierDTO;

public interface ProductSupplierService {

    ResponseProductSupplierDTO createProductSupplier(CreateProductSupplierDTO createProductSupplierDTO);
    ResponseProductSupplierDTO updateProductSupplier(UpdateProductSupplierDTO updateProductSupplierDTO, Long id);

    SupplierProductListDTO listProductsBySupplier(String companyName);



}
