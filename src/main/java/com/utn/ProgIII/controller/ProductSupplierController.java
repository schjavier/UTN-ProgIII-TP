package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.CreateProductSupplierDTO;
import com.utn.ProgIII.dto.ResponseProductSupplierDTO;
import com.utn.ProgIII.dto.SupplierProductListDTO;
import com.utn.ProgIII.dto.UpdateProductSupplierDTO;
import com.utn.ProgIII.service.interfaces.ProductSupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productSupplier")
@Tag(name = "Productos y Proveedores", description = "Operaciones relacionadas con la relación de productos y proveedores")

public class ProductSupplierController {

    private final ProductSupplierService productSupplierService;

    public ProductSupplierController(ProductSupplierService productSupplierService){
        this.productSupplierService = productSupplierService;
    }

    @PostMapping
    @Operation(summary = "Crea un ProductSupplier", description = "Crea una relacion entre un proveedor y producto")
    @ApiResponse(responseCode = "200", description = "Creado")
    @ApiResponse(responseCode = "404", description = "Datos malformados", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(description = "Un mensaje que tiene un error de usuario")
    ))

    public ResponseEntity<ResponseProductSupplierDTO> createProductSupplier(@Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Relacion entre proveedor y producto para crear")
                                                                                CreateProductSupplierDTO request
    ){

        ResponseProductSupplierDTO response = productSupplierService.createProductSupplier(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PatchMapping("/{id}")
    @ApiResponse(responseCode = "200",description = "Datos modificados")
    //@ApiResponse(responseCode = "400",description = "Datos malformados") esto no se lanza?
    @ApiResponse(responseCode = "404",description = "Relacion no encontrada", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "La relación que quiere editar no se encuentra")
    ))
    @Operation(summary = "Modifica los datos de una relacion", description = "Modifica el precio y el profit margin de una relacion")
    public ResponseEntity<ResponseProductSupplierDTO> modifyProductSupplier(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Relacion para modificar los precios y el profit margin")
            @Valid @RequestBody UpdateProductSupplierDTO request,
            @PathVariable Long id){

        ResponseProductSupplierDTO response = productSupplierService.updateProductSupplier(request, id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Busca todos los productos de un proveedor segun su nombre", description = "Busca todos los productos de un proveedor segun su nombre")
    @ApiResponse(responseCode = "200",description = "Lista devuelta", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = SupplierProductListDTO.class))
    ))
    @ApiResponse(responseCode = "404",description = "Proveedor inexistente", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "El proveedor no existe")
    ))
    @GetMapping("/filter/{companyName}")
    public ResponseEntity<SupplierProductListDTO> listAllProductsBySupplier(@PathVariable String companyName){

        SupplierProductListDTO response = productSupplierService.listProductsBySupplier(companyName);
        return ResponseEntity.ok(response);

    }


}
