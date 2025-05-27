package com.utn.ProgIII.controller;

import com.utn.ProgIII.model.ProductSupplier.dto.CreateProductSupplierDTO;
import com.utn.ProgIII.model.ProductSupplier.dto.ResponseProductSupplierDTO;
import com.utn.ProgIII.model.ProductSupplier.dto.SupplierProductListDTO;
import com.utn.ProgIII.model.ProductSupplier.dto.UpdateProductSupplierDTO;
import com.utn.ProgIII.service.interfaces.ProductSupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productSupplier")
@Tag(name = "Productos y Proveedores", description = "Operaciones relacionadas con productos y proveedores")
public class ProductSupplierController {

    private final ProductSupplierService productSupplierService;

    public ProductSupplierController(ProductSupplierService productSupplierService){
        this.productSupplierService = productSupplierService;
    }

    @PostMapping
    @Operation(summary = "Crea un ProductSupplier", description = "Crea una relacion entre un proveedor y producto")
    @ApiResponse(responseCode = "200", description = "Creado")
    @ApiResponse(responseCode = "400", description = "Datos malformados")
    public ResponseEntity<ResponseProductSupplierDTO> createProductSupplier(@Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Relacion entre provedor y producto para crear")
                                                                                CreateProductSupplierDTO request
    ){

        ResponseProductSupplierDTO response = productSupplierService.createProductSupplier(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PatchMapping("/{id}")
    @ApiResponse(responseCode = "200",description = "Datos modificados")
    @ApiResponse(responseCode = "400",description = "Datos malformados")
    @Operation(summary = "Modifica los datos de una relacion", description = "Modifica el precio y el profit margin de una relacion")
    public ResponseEntity<ResponseProductSupplierDTO> modifyProductSupplier(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Relacion para modificar los precios y el profit margin")
            @Valid @RequestBody UpdateProductSupplierDTO request,
            @PathVariable Long id){

        ResponseProductSupplierDTO response = productSupplierService.updateProductSupplier(request, id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Busca todos los productos de un provedor segun su nombre", description = "Busca todos los productos de un provedor segun su nombre")
    @ApiResponse(responseCode = "200",description = "Lista devuelta")
    @ApiResponse(responseCode = "400",description = "Provedor inexistente")
    @GetMapping("/filter/{companyName}")
    public ResponseEntity<SupplierProductListDTO> listAllProductsBySupplier(@PathVariable String companyName){

        SupplierProductListDTO response = productSupplierService.listProductsBySupplier(companyName);
        return ResponseEntity.ok(response);

    }


}
