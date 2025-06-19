package com.utn.ProgIII.mapper;

import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import com.utn.ProgIII.dto.ResponseProductSupplierDTO;
import com.utn.ProgIII.model.Supplier.Supplier;
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

    public ProductSupplier fromDtoToEntity(ResponseProductSupplierDTO dto) {
        Product product = new Product();
        product.setIdProduct(dto.idProduct());
        product.setName(dto.productName());

        Supplier supplier = new Supplier();
        supplier.setIdSupplier(dto.idSupplier());
        supplier.setCompanyName(dto.supplierName());

        ProductSupplier productSupplier = new ProductSupplier();
        productSupplier.setIdProductSupplier(dto.idProductSupplier());
        productSupplier.setProduct(product);
        productSupplier.setSupplier(supplier);
        productSupplier.setCost(dto.cost());
        productSupplier.setProfitMargin(dto.profitMargin());
        productSupplier.setPrice(dto.price());

        return productSupplier;
    }
}

