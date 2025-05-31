package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.CreateProductSupplierDTO;
import com.utn.ProgIII.dto.ResponseProductSupplierDTO;
import com.utn.ProgIII.dto.SupplierProductListDTO;
import com.utn.ProgIII.dto.UpdateProductSupplierDTO;
import com.utn.ProgIII.service.interfaces.ProductSupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

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

    @Operation(summary = "Actualiza los precios de los productos de un proveedor masivamente",
            description = "Actualiza los precios de los productos de un proveedor por medio de una lista en formato .csv, " +
                    "pero solamente si la relacion entre el producto y el proveedor ya existen. Finalmente devuelve una lista con aquellos " +
                    "productos que no pudieron ser cargados")
    @ApiResponse(responseCode = "200",description = "Actualizacion realizada, devuelve listado con aquellos productos que no pudieron ser cargados")
    @ApiResponse(responseCode = "500",description = "El servidor no pudo procesar el archivo")
    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updateWithFile(@RequestParam("file") MultipartFile file, @RequestParam Long idSupplier) {
        String filename = file.getOriginalFilename();
        String response;

        try {
            String tmpdir = Files.createTempDirectory("tmpDirPrefix").toFile().getAbsolutePath();
            file.transferTo(new File(tmpdir + "\\" + filename));
            response = productSupplierService.uploadCsv(tmpdir + "\\" + filename, idSupplier);
        } catch (IOException e) {
            System.out.println("Error subiendo el archivo: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(response);
    }

}
