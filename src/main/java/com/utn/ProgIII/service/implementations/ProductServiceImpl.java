package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.ProductDTO;

import com.utn.ProgIII.exceptions.InvalidProductStatusException;
import com.utn.ProgIII.exceptions.ProductNotFoundException;
import com.utn.ProgIII.mapper.ProductMapper;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import com.utn.ProgIII.repository.ProductRepository;
import com.utn.ProgIII.repository.ProductSupplierRepository;
import com.utn.ProgIII.service.interfaces.AuthService;
import com.utn.ProgIII.service.interfaces.ProductService;
import jakarta.transaction.Transactional;
import com.utn.ProgIII.validations.ProductValidations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductSupplierRepository productSupplierRepository;
    private final ProductValidations productValidations;
    private final AuthService authService;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ProductSupplierRepository productSupplierRepository, ProductValidations productValidations, AuthService authService) {

        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productSupplierRepository = productSupplierRepository;
        this.productValidations = productValidations;
        this.authService = authService;
    }


    @Override
    public ProductDTO getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Producto no encontrado"));

        if((authService.hasRole("ROLE_EMPLOYEE") && authService.getRoleCount() == 1) && product.getStatus() == ProductStatus.DISABLED)
        {
            throw new ProductNotFoundException("Producto no encontrado");
        }


        return productMapper.toProductDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProduct() {

        List<Product> products = new ArrayList<>();



        if(authService.hasRole("ROLE_MANAGER"))
        {
            products = productRepository.findAll();
        } else if (authService.hasRole("ROLE_EMPLOYEE")) {
            products = productRepository.findByStatus(ProductStatus.ENABLED);
        }


        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product : products){
            productDTOList.add(productMapper.toProductDTO(product));
        }
        return productDTOList;
    }

    @Override
    public List<ProductDTO> getAllProductByStatus(String status) {

        try {
            ProductStatus value = ProductStatus.valueOf(status.toUpperCase());

            List<Product> products = productRepository.findByStatus(value);
            List<ProductDTO> productDTOList = new ArrayList<>();

            for (Product product : products){
                productDTOList.add(productMapper.toProductDTO(product));
            }
            return productDTOList;

        } catch (IllegalArgumentException e){
            throw new InvalidProductStatusException("El estado ingresado es invalido");
        }
    }

    @Override
    public List<ProductDTO> getProductByName(String name) {
        List<Product> products = productRepository.findByNameContaining(name);
        List<ProductDTO> productDTOS = new ArrayList<>();

        if(authService.hasRole("ROLE_EMPLOYEE") && authService.getRoleCount() == 1)
        {
            products = products.stream().filter(x -> x.getStatus() == ProductStatus.ENABLED).toList();
        }

        for(Product product : products){
            productDTOS.add(productMapper.toProductDTO(product));
        }

        return productDTOS;
    }

    @Override
    public ProductDTO createProductDto(ProductDTO productDto) {

        Product product = productMapper.toEntity(productDto);
        productValidations.validateProductNameExists(product);
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
    @Transactional
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        product.setStatus(ProductStatus.DISABLED);
        productSupplierRepository.removeAllByProduct_IdProduct(id);

        productRepository.save(product);

    }
}
