package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.model.Product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {


    public ProductDTO toProductDTO (Product product) {
        Integer idProduct = product.getIdProduct();
        String name = product.getName();
        double cost = product.getCost();
        double profitMargin = product.getProfitMargin();
        double price = product.getPrice();

        return new ProductDTO(idProduct,name,cost,profitMargin,price);
    }


    public Product toEntity (ProductDTO productDTO){
        Product result = new Product();

        result.setName(productDTO.name());
        result.setCost(productDTO.cost());
        result.setProfitMargin(productDTO.profitMargin());
        result.setPrice(productDTO.price());

        return result;
    }


}
