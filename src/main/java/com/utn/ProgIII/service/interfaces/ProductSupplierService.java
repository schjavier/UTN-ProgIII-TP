package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.*;
import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;

public interface ProductSupplierService {

    ResponseProductSupplierDTO createProductSupplier(CreateProductSupplierDTO createProductSupplierDTO);
    ResponseProductSupplierDTO updateProductSupplier(UpdateProductSupplierDTO updateProductSupplierDTO, Long id);
    SupplierProductListDTO listProductsBySupplier(String companyName);
    String uploadCsv(String filepath, Long idSupplier);
    String uploadCsv(String filepath, Long idSupplier, BigDecimal bulkProfitMargin);

    //nuevo
    Object getProductSupplierById(Long id, Authentication authentication);
    SupplierProductListToEmployeeDTO listProductSupplierToEmployee (String companyName);
}
