package com.utn.ProgIII.model.ProductSupplier;

import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.model.Product.Product;

import java.util.List;

public record SupplierProductListDTO(Long idSupplier, String companyName, List<ExtendedProductDTO> productsList) {
}
