package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.model.Product.ProductStatus;
import com.utn.ProgIII.service.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @ApiResponse(responseCode = "201", description = "Producto creado")
    @ApiResponse(responseCode = "400",description = "Error en datos introducidos", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "(un mensaje con todos los errores del usuario)")
    ))

    public ResponseEntity<ProductDTO> addProduct (
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "El producto para crear")
            @RequestBody ProductDTO productDTO
    ){

        ProductDTO response = productService.createProductDto(productDTO);

        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    //muestra 1 solo producto

    @GetMapping ("/{id}")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "Proveedor no encontrado")
    ))
    @Operation(summary = "Se muestra un producto por id", description = "Se muestra el producto con todos sus datos")
    public ResponseEntity<ProductDTO> getProductById (@PathVariable Long id) {
        ProductDTO response = productService.getProductById(id);

        return ResponseEntity.ok(response);
    }

    //muestra la lista de todos los productos
    @GetMapping()
    @Operation(summary = "Devuelve todos los productos", description = "Devuelve todos los productos")
    @ApiResponse(responseCode = "200", description = "Lista devuelta correctamente", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))
    ))
    public ResponseEntity<List<ProductDTO>> getAllProduct (){
        List <ProductDTO> response = productService.getAllProduct();

        return ResponseEntity.ok(response);
    }

    //muestra la lista de todos los productos por estado
    @GetMapping("/search/status/{status}")
    @ApiResponse(responseCode = "200", description = "Lista de productos segun estado devuelto correctamente", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))
    ))
    @ApiResponse(responseCode = "400", description = "Error de datos introducidos por usuario", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "El estado ingresado es invalido")
    ))
    @Operation(summary = "Se muestra una lista de productos por su status", description = "Se muestra una lista segun su status")

    public ResponseEntity<List<ProductDTO>> getAllProductByStatus(@PathVariable String status){

        List <ProductDTO> response = productService.getAllProductByStatus(status);

        return ResponseEntity.ok(response);
    }

    //muestra lista de productos (busca por nombre)
    @GetMapping("/search/name/{name}")
    @Operation(summary = "Se muestra una lista de productos por nombres", description = "Se muestra una lista de productos por nombres")
    @ApiResponse(responseCode = "200", description = "Lista de productos segun nombre")
    public ResponseEntity<List<ProductDTO>> getProductByName(@PathVariable String name){

        List<ProductDTO> response = productService.getProductByName(name);

        return ResponseEntity.ok(response);
    }

    //modificar un producto
    @PutMapping("/{id}")
    @Operation(summary = "Se actualiza los datos de un producto")
    @ApiResponse(responseCode = "200",description = "Actualizacion completa")
    @ApiResponse(responseCode = "400",description = "Datos malcolocados", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "(un mensaje con todos los errores del usuario)")
    ))
    @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "Producto no encontrado")
    ))

    public ProductDTO update (@PathVariable Long id, @RequestBody ProductDTO modifyProductDTO){

       return productService.updateProduct(id,modifyProductDTO);
    }

    //Baja logica de un producto (modifica solo el estado)
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Eliminado correctamente", content = @Content())
    @ApiResponse(responseCode = "404", description = "Producto no existe", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "Producto no encontrado")
    ))
    @Operation(summary = "Se hace una baja logica de un producto", description = "Se hace una baja logica segun su id")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
