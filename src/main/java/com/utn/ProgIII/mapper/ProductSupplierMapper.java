package com.utn.ProgIII.mapper;

import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import com.utn.ProgIII.model.ProductSupplier.dto.ResponseProductSupplierDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductSupplierMapper {

    public ResponseProductSupplierDTO fromEntityToDto(ProductSupplier productSupplier) {
        return new ResponseProductSupplierDTO(
                productSupplier.getIdProductSupplier(),
                productSupplier.getProduct().getIdProduct(),
                productSupplier.getProduct().getName(),
                productSupplier.getSupplier().getIdSupplier(),
                productSupplier.getSupplier().getCompanyName(),
                productSupplier.getCost(),
                productSupplier.getProfitMargin(),
                productSupplier.getPrice()
        );
    }
}

