package com.utn.ProgIII.service;

import com.utn.ProgIII.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO getProductById(Long id);
    List <ProductDTO>  getAllProduct ();
    List <ProductDTO> getProductByName(String name);
    ProductDTO createProductDto (ProductDTO prductoDto);
    ProductDTO updateProduct (ProductDTO productDto);
    void deleteProduct (Long idProduct);


}
