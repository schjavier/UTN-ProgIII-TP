package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.dto.ViewAddressDTO;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import com.utn.ProgIII.exceptions.SupplierNotFoundException;
import com.utn.ProgIII.service.implementations.SupplierServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/supplier")
@Tag(name = "Proveedores", description = "Operaciones relacionades con los proveedores")
public class SupplierController {

    @Autowired
    private SupplierServiceImpl supplierService;

    /**
     * Responde a peticiones http con la url "/supplier" usando POST
     * @param supplier_DTO Un DTO que representa el usuario cargado desde el frontend
     * @return Un usuario cargado desde el backend
     */
    @Operation(summary = "Agregar un proveedor", description = "Agrega un proveedor")
    @PostMapping
    @ApiResponse(responseCode = "201", description = "Proveedor creado")
    @ApiResponse(responseCode = "400", description = "Error en datos introducidos")
    public ResponseEntity<ViewSupplierDTO> addSupplier(
            @RequestBody AddSupplierDTO supplier_DTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierService.createSupplier(supplier_DTO));
    }

    /**
     * Responde a peticiones http con la url "/page{page}/{size}"
     * @param page Page denota una "pagina" que contiene una cantidad de "size". Comienza en 1
     * @param size Define el tamaño de la pagina
     * @return Una pagina con todos los proveedores que pueda conseguir.
     */
    @GetMapping("/page{page}/{size}")
    @ApiResponse(responseCode = "200", description = "Encontrado")
    @ApiResponse(responseCode = "404", description = "No encontrado")
    @Operation(summary = "Busca una pagina de proveedores", description = "Lista una pagina de provedores")
    public ResponseEntity<List<ViewSupplierDTO>> getSuppliers(
            @Parameter(description = "N° Pagina (comienza en 1)", example = "1")
            @PathVariable int page,
            @Parameter(description = "Tamaño de la pagina", example = "5")
            @PathVariable int size)
    {
        return ResponseEntity.ok(supplierService.listSuppliers(page,size));
    }

    /**
     * Consigue un proveedor con su respectivo id
     * @param id El id del proveedor
     * @return Un DTO de proveedor
     */
    @ApiResponse(responseCode = "200", description = "Encontrado")
    @ApiResponse(responseCode = "404", description = "No encontrado")
    @GetMapping("/{id}")
    @Operation(summary = "Busca un proveedor", description = "Busca un provedor segun id")
    public ResponseEntity<ViewSupplierDTO> getSupplier(
            @Parameter(description = "Id de proveedor", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(supplierService.viewOneSupplier(id));
    }

    /**
     * Actualiza un proveedor existente con todos sus datos segun la id que tiene. Responde a peticiones http con la url "/{id}"
     * @param supplier_DTO Un objeto DTO que tiene todos los datos del proveedor
     * @param id El id del proveedor para modificar
     * @return Un DTO que muestra todo el proveedor con sus cambios.
     */
    @ApiResponse(responseCode = "400", description = "Error en datos introducidos", content = @Content(
            schema = @Schema(implementation = SupplierNotFoundException.class)
    ))
    @ApiResponse(responseCode = "200", description = "Proveedor Actualizado", content = @Content(
            schema = @Schema(implementation = ViewSupplierDTO.class)
    ))
    @PutMapping("/{id}")
    @Operation(summary = "Modifica los datos del proveedor")
    public ResponseEntity<?> updateSupplier(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Los datos actualizados")
            @RequestBody AddSupplierDTO supplier_DTO,
            @Parameter(description = "El id del proveedor a modificar", example = "1")
            @PathVariable Long id)
    {
        return ResponseEntity.ok(supplierService.updateSupplier(supplier_DTO,id));
    }

    /**
     * Elimina un proveedor de la base de datos. Responde a peticiones http con la url "/{id}"
     * @param id El id del proveedor para eliminar
     * @return Un booleano verdadero, en caso de exito.
     */
    @ApiResponse(responseCode = "200", description = "Eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Proveedor No encontrado")
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un provedor segun su id")
    public ResponseEntity<Boolean> deleteSupplier(
            @Parameter(description = "El id del provedor para eliminar <b>permanentemente</b>", example = "1")
            @PathVariable int id)
    {
        return ResponseEntity.ok(supplierService.deleteSupplier(id));
    }
}
