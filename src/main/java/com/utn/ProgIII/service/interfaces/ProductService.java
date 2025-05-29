package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.model.Product.ProductStatus;

import java.util.List;

public interface ProductService {

    ProductDTO getProductById(Long id);
    List <ProductDTO>  getAllProduct ();
    List <ProductDTO> getAllProductByStatus (String status);
    List <ProductDTO> getProductByName(String name);
    ProductDTO createProductDto (ProductDTO prductoDto);
    ProductDTO updateProduct (Long id, ProductDTO productDto);
    void deleteProduct (Long id);


}
