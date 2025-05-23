package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.model.Product.ProductStatus;
import com.utn.ProgIII.service.interfaces.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/product")


public class ProductController {

    private final ProductService productService;


    public ProductController (ProductService productService) {
        this.productService = productService;
    }

    //crea un producto nuevo
    @PostMapping
    public ResponseEntity<ProductDTO> addProduct (@RequestBody ProductDTO productDTO){

        ProductDTO response = productService.createProductDto(productDTO);

        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    //muestra 1 solo producto

    @GetMapping ("/{id}")
    public ResponseEntity<ProductDTO> getProductById (@PathVariable Long id) {
        ProductDTO response = productService.getProductById(id);

        return ResponseEntity.ok(response);
    }

    //muestra la lista de todos los productos
    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getAllProduct (){
        List <ProductDTO> response = productService.getAllProduct();

        return ResponseEntity.ok(response);
    }

    //muestra la lista de todos los productos por estado
    @GetMapping("/search/status/{status}")
    public ResponseEntity<List<ProductDTO>> getAllProductByStatus(@PathVariable ProductStatus status){

        List <ProductDTO> response = productService.getAllProductByStatus(status);

        return ResponseEntity.ok(response);
    }

    //muestra lista de productos (busca por nombre)
    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<ProductDTO>> getProductByName(@PathVariable String name){

        List<ProductDTO> response =productService.getProductByName(name);

        return ResponseEntity.ok(response);
    }

    //modificar un producto
    @PutMapping("/{id}")
    public ProductDTO update (@PathVariable Long id, @RequestBody ProductDTO modifyProductDTO){

       return productService.updateProduct(id,modifyProductDTO);
    }

    //Baja logica de un producto (modifica solo el estado)
    @PatchMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    
}
