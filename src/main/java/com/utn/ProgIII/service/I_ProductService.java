package com.utn.ProgIII.service;

import com.utn.ProgIII.dto.ProductDTO;

import java.util.List;

public interface I_ProductService {

    ProductDTO getProductById(int id);
    List <ProductDTO>  getAllProduct ();
    ProductDTO createProductDto (ProductDTO prductoDto);
    ProductDTO updateProduct (ProductDTO productDto);
    void deleteProduct (int idProduct);


}
