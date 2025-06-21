package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.*;
import com.utn.ProgIII.service.interfaces.ProductSupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;

@RestController
@RequestMapping("/productSupplier")
@Tag(name = "Productos y Proveedores", description = "Operaciones relacionadas con la relación de productos y proveedores")

/*
  Clase que maneja requests sobre la relación de productos y proveedores
 */
public class ProductSupplierController {

    private final ProductSupplierService productSupplierService;

    public ProductSupplierController(ProductSupplierService productSupplierService){
        this.productSupplierService = productSupplierService;
    }

    @PostMapping
    @Operation(summary = "Crea un ProductSupplier", description = "Crea una relación entre un proveedor y producto")
    @ApiResponse(responseCode = "200", description = "Creado")
    @ApiResponse(responseCode = "400", description = "Datos erróneos", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(description = "(Un mensaje que tiene los errores del usuario)")
    ))
    @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(description = "Un mensaje que tiene un error de usuario")
    ))
    public ResponseEntity<ResponseProductSupplierDTO> createProductSupplier(@Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Relación entre proveedor y producto para crear")
                                                                                CreateProductSupplierDTO request
    ){

        ResponseProductSupplierDTO response = productSupplierService.createProductSupplier(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PatchMapping("/{id}")
    @ApiResponse(responseCode = "200",description = "Datos modificados")
    @ApiResponse(responseCode = "404",description = "Relación no encontrada", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "La relación que quiere editar no se encuentra")
    ))
    @Operation(summary = "Modifica los datos de una relación", description = "Modifica el precio y el profit margin de una relación")
    public ResponseEntity<ResponseProductSupplierDTO> modifyProductSupplier(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Relación para modificar los precios y el profit margin")
            @Valid @RequestBody UpdateProductSupplierDTO request,
            @PathVariable Long id){

        ResponseProductSupplierDTO response = productSupplierService.updateProductSupplier(request, id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Busca todos los productos de un proveedor según su nombre", description = "Busca todos los productos de un proveedor según su nombre")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de precios devuelta",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SupplierProductListDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "manager-view",
                                    summary = "Vista para rol MANAGER o superior",
                                    description = "Incluye todos los precios (costo y venta) y márgenes de ganancia",
                                    value = "{\"idProduct\": 1, \"name\": \"Producto\", \"prices\": [{\"idSupplier\": 1, \"companyName\": \"Proveedor\", \"cost\": 100.00, \"profitMargin\": 20.00, \"price\": 120.00, \"dollarPrice\": 0.1043}]}"
                            ),
                            @ExampleObject(
                                    name = "manager-view-no-dollar-api",
                                    summary = "Vista para rol MANAGER o superior (API dólar caída)",
                                    description = "Incluye todos los precios (costo y venta) y márgenes de ganancia",
                                    value = "{\"idProduct\": 1, \"name\": \"Producto\", \"prices\": [{\"idSupplier\": 1, \"companyName\": \"Proveedor\", \"cost\": 100.00, \"profitMargin\": 20.00, \"price\": 120.00, \"dollarPrice\": \"not available\"}]}"
                            ),
                            @ExampleObject(
                                    name = "employee-view",
                                    summary = "Vista para rol EMPLOYEE",
                                    description = "Solo muestra precios finales sin incluir márgenes de ganancia",
                                    value = "{\"idProduct\": 1, \"name\": \"Producto\", \"prices\": [{\"idSupplier\": 1, \"companyName\": \"Proveedor\", \"price\": 120.00, \"dollarPrice\": 0.1043}]}"
                            )
                    }))
    @ApiResponse(responseCode = "404",description = "Proveedor inexistente", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "El proveedor no existe")
    ))
    @GetMapping("/filter/{companyName}")
    public ResponseEntity<SupplierProductListDTO> listAllProductsBySupplier(@PathVariable String companyName){

        SupplierProductListDTO response = productSupplierService.listProductsBySupplier(companyName);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/filter-product/{productId}")
    @Operation(summary = "Devuelve una lista de precios de un producto según su id. Contenidos dependen del permiso del usuario.")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de precios devuelta",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductPricesDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "manager-view",
                                    summary = "Vista para rol MANAGER o superior",
                                    description = "Incluye todos los precios (costo y venta) y márgenes de ganancia",
                                    value = "{\"idProduct\": 1, \"name\": \"Producto\", \"prices\": [{\"idSupplier\": 1, \"companyName\": \"Proveedor\", \"cost\": 100.00, \"profitMargin\": 20.00, \"price\": 120.00, \"dollarPrice\": 0.1043}]}"
                            ),
                            @ExampleObject(
                                    name = "manager-view-no-dollar-api",
                                    summary = "Vista para rol MANAGER o superior (API dólar caída)",
                                    description = "Incluye todos los precios (costo y venta) y márgenes de ganancia",
                                    value = "{\"idProduct\": 1, \"name\": \"Producto\", \"prices\": [{\"idSupplier\": 1, \"companyName\": \"Proveedor\", \"cost\": 100.00, \"profitMargin\": 20.00, \"price\": 120.00, \"dollarPrice\": \"not available\"}]}"
                            ),
                            @ExampleObject(
                                    name = "employee-view",
                                    summary = "Vista para rol EMPLOYEE",
                                    description = "Solo muestra precios finales sin incluir márgenes de ganancia",
                                    value = "{\"idProduct\": 1, \"name\": \"Producto\", \"prices\": [{\"idSupplier\": 1, \"companyName\": \"Proveedor\", \"price\": 120.00, \"dollarPrice\": 0.1043}]}"
                            )
                    }
            )
    )
    public ResponseEntity<ProductPricesDTO> listAllPricesByProduct(@PathVariable Long productId){
        return ResponseEntity.ok(productSupplierService.listPricesByProduct(productId));
    }



    @Operation(summary = "Actualiza los precios de los productos de un proveedor masivamente",
            description = "Actualiza los precios de los productos de un proveedor por medio de una lista en formato .csv, " +
                    "pero solamente si la relación entre el producto y el proveedor ya existen. Finalmente, devuelve una lista con aquellos " +
                    "productos que no pudieron ser cargados")
    @ApiResponse(responseCode = "200",description = "Actualización realizada, devuelve listado con aquellos productos que no pudieron ser cargados")
    @ApiResponse(responseCode = "500",description = "El servidor no pudo procesar el archivo")
    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updateWithFile(@RequestParam("file") MultipartFile file,@RequestParam Long idSupplier) {
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

    @Operation(summary = "Actualiza los precios de los productos de un proveedor masivamente y vincula aquellos sin relación existente",
            description = "Actualiza los precios de los productos de un proveedor por medio de una lista en formato .csv, " +
                    "y carga nuevas entradas con un porcentaje de ganancia definido si aún no están relacionados. Finalmente, devuelve una lista con aquellos " +
                    "productos que no pudieron ser cargados")
    @ApiResponse(responseCode = "200",description = "Actualización realizada, devuelve listado con aquellos productos que no pudieron ser cargados")
    @ApiResponse(responseCode = "500",description = "El servidor no pudo procesar el archivo")
    @PostMapping(path = "/uploadNonRelatedProducts", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updateWithFileAndProfitMargin(@RequestParam("file") MultipartFile file,
                                                                @RequestParam Long idSupplier,
                                                                @RequestParam(required = false) BigDecimal bulkProfitMargin) {
        String filename = file.getOriginalFilename();
        String response;
        if (bulkProfitMargin == null) bulkProfitMargin = BigDecimal.valueOf(0);

        try {
            String tmpdir = Files.createTempDirectory("tmpDirPrefix").toFile().getAbsolutePath();
            file.transferTo(new File(tmpdir + "\\" + filename));
            response = productSupplierService.uploadCsv(tmpdir + "\\" + filename, idSupplier, bulkProfitMargin);
        } catch (IOException e) {
            System.out.println("Error subiendo el archivo: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(response);
    }

}
