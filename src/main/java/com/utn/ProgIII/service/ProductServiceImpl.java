package com.utn.ProgIII.service;

import com.utn.ProgIII.dto.ProductDTO;

import com.utn.ProgIII.exceptions.ProductNotFoundException;
import com.utn.ProgIII.mapper.ProductMapper;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.repository.I_ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements I_ProductService {

    private final I_ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(I_ProductRepository productRepository, ProductMapper productMapper) {

        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    public ProductDTO getProductById(int id) {

        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Prodcuto no encontrado"));
        return productMapper.toProductDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProduct() {

        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product : products){
            productDTOList.add(productMapper.toProductDTO(product));
        }
        return productDTOList;
    }

    @Override
    public ProductDTO createProductDto(ProductDTO productDto) {

        Product product = productMapper.toEntity(productDto);
        product = productRepository.save(product);

        return productMapper.toProductDTO(product);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDto) {

        Product product = productMapper.toEntity(productDto);

        product = productRepository.save(product);

        return productMapper.toProductDTO(product);
    }

    @Override
    public void deleteProduct(int idProduct) {

        if (!productRepository.existsById(idProduct)){
            throw new ProductNotFoundException("Producto con ID: " + idProduct + " no encontrado");
        }
        productRepository.deleteById(idProduct);
    }

}
