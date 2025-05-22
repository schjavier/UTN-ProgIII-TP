package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.ProductDTO;

import com.utn.ProgIII.exceptions.ProductNotFoundException;
import com.utn.ProgIII.mapper.ProductMapper;
import com.utn.ProgIII.model.Product.Product;
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

        Product product = productRepository.findById(id.intValue())
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
    public List<ProductDTO> getProductByName(String name) {
        List<Product> products = productRepository.findByName(name);
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
    public ProductDTO updateProduct(ProductDTO productDto) {

        Product product = productMapper.toEntity(productDto);

        product = productRepository.save(product);

        return productMapper.toProductDTO(product);
    }

    @Override
    public void deleteProduct(Long idProduct) {

        if (!productRepository.existsById(idProduct.intValue())){
            throw new ProductNotFoundException("Producto con ID: " + idProduct + " no encontrado");
        }
        productRepository.deleteById(idProduct.intValue());
    }

}
