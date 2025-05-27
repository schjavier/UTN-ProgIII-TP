package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.model.Product.ProductStatus;
import com.utn.ProgIII.service.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/product")

@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductController {

    private final ProductService productService;


    public ProductController (ProductService productService) {
        this.productService = productService;
    }


    //crea un producto nuevo
    @PostMapping
    @Operation(summary = "Se agrega un producto", description = "Se agrega un producto con los datos del usuario")
    public ResponseEntity<ProductDTO> addProduct (
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "El producto para crear")
            @RequestBody ProductDTO productDTO
    ){

        ProductDTO response = productService.createProductDto(productDTO);

        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    //muestra 1 solo producto

    @GetMapping ("/{id}")
    @Operation(summary = "Se muestra un producto por id", description = "Se muestra el producto con todos sus datos")
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
    @Operation(summary = "Se muestra una lista de productos por su status", description = "Se muestra una lista segun su status")
    public ResponseEntity<List<ProductDTO>> getAllProductByStatus(@PathVariable ProductStatus status){

        List <ProductDTO> response = productService.getAllProductByStatus(status);

        return ResponseEntity.ok(response);
    }

    //muestra lista de productos (busca por nombre)
    @GetMapping("/search/name/{name}")
    @Operation(summary = "Se muestra una lista de productos por nombres", description = "Se muestra una lista de productos por nombres")
    public ResponseEntity<List<ProductDTO>> getProductByName(@PathVariable String name){

        List<ProductDTO> response = productService.getProductByName(name);

        return ResponseEntity.ok(response);
    }

    //modificar un producto
    @PutMapping("/{id}")
    @Operation(summary = "Se actualiza los datos de un producto")
    public ProductDTO update (@PathVariable Long id, @RequestBody ProductDTO modifyProductDTO){

       return productService.updateProduct(id,modifyProductDTO);
    }

    //Baja logica de un producto (modifica solo el estado)
    @DeleteMapping("/{id}")
    @Operation(summary = "Se hace una baja logica de un producto", description = "Se hace una baja logica segun su id")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
