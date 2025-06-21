package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductDTO getProductById(Long id);
    List <ProductDTO> getAllProduct ();
    List <ProductDTO> getAllProductByStatus (String status);
    List <ProductDTO> getProductByName(String name);
    ProductDTO createProductDto (ProductDTO prductoDto);
    ProductDTO updateProduct (Long id, ProductDTO productDto);
    void deleteProduct (Long id);
    Page<ProductDTO> getProductPage(Pageable pageable);

}
