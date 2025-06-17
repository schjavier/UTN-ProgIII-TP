package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.*;

import java.math.BigDecimal;

public interface ProductSupplierService {

    ResponseProductSupplierDTO createProductSupplier(CreateProductSupplierDTO createProductSupplierDTO);
    ResponseProductSupplierDTO updateProductSupplier(UpdateProductSupplierDTO updateProductSupplierDTO, Long id);
    SupplierProductListDTO listProductsBySupplier(String companyName);
    ProductPricesDTO listPricesByProduct(Long idProduct);
    String uploadCsv(String filepath, Long idSupplier);
    String uploadCsv(String filepath, Long idSupplier, BigDecimal bulkProfitMargin);
}
