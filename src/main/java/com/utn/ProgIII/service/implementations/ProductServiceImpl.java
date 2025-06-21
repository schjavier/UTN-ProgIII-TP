package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.ProductDTO;

import com.utn.ProgIII.exceptions.InvalidProductStatusException;
import com.utn.ProgIII.exceptions.InvalidRequestException;
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
import org.apache.commons.lang3.EnumUtils;
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


    /**
     * Busca un producto por su ID
     * @param id ID del producto
     * @return <code>ProductDTO</code>
     */
    @Override
    public ProductDTO getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Producto no encontrado"));

        if(authService.isEmployee() && product.getStatus() == ProductStatus.DISABLED)
        {

            throw new ProductNotFoundException("Producto no encontrado");

        }

        return productMapper.toProductDTO(product);
    }

    /**
     * Busca todos los productos
     * @return Una lista de <code>ProductDto</code>
     * @see ProductDTO
     */
    @Override
    public List<ProductDTO> getAllProduct() {

        List<Product> products = new ArrayList<>();

        if(!authService.isEmployee())
        {
            products = productRepository.findAll();

        } else {

            products = productRepository.findByStatus(ProductStatus.ENABLED);

        }

        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product : products){
            productDTOList.add(productMapper.toProductDTO(product));
        }
        return productDTOList;
    }

    /**
     * Busca productos según estado
     * @param status El estado del producto
     * @return Lista <code>ProductDto</code>
     * @see ProductStatus
     * @see ProductDTO
     *
     */
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
            throw new InvalidProductStatusException("El estado ingresado es inválido");
        }
    }

    /**
     * Busca un producto según nombre
     * @param name El nombre del producto, se usa un LIKE de sql
     * @return Retorna una lista de <code>ProductDto</code>
     * @see ProductDTO
     */

    @Override
    public List<ProductDTO> getProductByName(String name) {
        List<Product> products = productRepository.findByNameContaining(name);
        List<ProductDTO> productDTOS = new ArrayList<>();

        if(authService.isEmployee())
        {
            products = products.stream().filter(x -> x.getStatus() == ProductStatus.ENABLED).toList();
        }

        for(Product product : products){
            productDTOS.add(productMapper.toProductDTO(product));
        }

        return productDTOS;
    }

    /**
     * Crea un producto nuevo y lo guarda en la base de datos
     * @param productDto Un DTO de un producto que se creará
     * @return Un <code>ProductDto</code> del producto creado
     * @see ProductDTO
     */

    @Override
    public ProductDTO createProductDto(ProductDTO productDto) {

        Product product = productMapper.toEntity(productDto);
        productValidations.validateProductNameExists(product);
        product = productRepository.save(product);

        return productMapper.toProductDTO(product);
    }

    /**
     * Se actualiza un producto según su ID
     * @param id ID del producto que se modificará
     * @param productDto Los datos para modificar el producto
     * @return Un ProductDTO del producto modificado
     * @see ProductDTO
     */
    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDto) {

        if(!EnumUtils.isValidEnum(ProductStatus.class,productDto.status()))
        {
            throw new InvalidRequestException("El estado de producto ingresado no es válido");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        product.setName(productDto.name());
        product.setStatus(ProductStatus.valueOf(productDto.status().toUpperCase()));

        if(product.getStatus() == ProductStatus.DISABLED)
        {
            productSupplierRepository.removeAllByProduct_IdProduct(product.getIdProduct());
        }

        product = productRepository.save(product);
        return productMapper.toProductDTO(product);
    }

    /**
     * Se da de baja (lógica) un producto según su ID, también se eliminan las relaciones de los proveedores
     * @param id identificador único del producto
     */
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
