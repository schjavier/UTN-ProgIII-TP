package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.ProductDTO;

import com.utn.ProgIII.exceptions.ProductNotFoundException;
import com.utn.ProgIII.mapper.ProductMapper;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import com.utn.ProgIII.repository.ProductRepository;
import com.utn.ProgIII.service.interfaces.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {

        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    public ProductDTO getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Producto no encontrado"));

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
    public List<ProductDTO> getAllProductByStatus(ProductStatus status) {

        List<Product> products = productRepository.findByStatus(status);
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product : products){
            productDTOList.add(productMapper.toProductDTO(product));
        }
        return productDTOList;
    }

    @Override
    public List<ProductDTO> getProductByName(String name) {
        List<Product> products = productRepository.findByNameContaining(name);
        List<ProductDTO> productDTOS = new ArrayList<>();

        for(Product product:products){
            productDTOS.add(productMapper.toProductDTO(product));
        }

        return productDTOS;
    }

    @Override
    public ProductDTO createProductDto(ProductDTO productDto) {

        Product product = productMapper.toEntity(productDto);
        product = productRepository.save(product);

        return productMapper.toProductDTO(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        product.setName(productDto.name());
        product.setStatus(ProductStatus.valueOf(productDto.status().toUpperCase()));

        product = productRepository.save(product);

        return productMapper.toProductDTO(product);
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        product.setStatus(ProductStatus.DISABLED);

        productRepository.save(product);

    }
}
