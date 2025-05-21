package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.service.I_ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/product")
@Controller

public class ProductController {

    private final I_ProductService productService;

    @Autowired
    public ProductController (I_ProductService productService) {
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

    //muestra lista de productos (busca por nombre)
    @GetMapping("{name}")
    public ResponseEntity<List<ProductDTO>> getProductByName(@PathVariable String name){

        List<ProductDTO> response =productService.getProductByName(name);

        return ResponseEntity.ok(response);
    }

    //modificar un producto
    @PutMapping("/{id}")
    public ResponseEntity <ProductDTO> update (
            @PathVariable Long id,
            @RequestBody ProductDTO modifyProductDTO
            ){
        try {

            if (id == modifyProductDTO.idProduct()){

                productService.updateProduct(modifyProductDTO);
                return ResponseEntity.ok(modifyProductDTO);
            }
            else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
           throw e;
        }
    }



    //En caso de usar el delete, deberiamos tener un campo de baja logica en el producto.

  /*
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct (@PathVariable Long id){

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

   */





}
