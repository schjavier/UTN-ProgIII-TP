package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import com.utn.ProgIII.service.implementations.SupplierServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
    @ApiResponse(responseCode = "201", description = "Proveedor creado", content = {
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ViewSupplierDTO.class)
            )
    })
    @ApiResponse(responseCode = "400", description = "Error en datos introducidos", content = {
            @Content(
                    mediaType = "text/plain;charset=UTF-8",
                    schema = @Schema(example = "(Un mensaje de error mostrando los errores del usuario)")
            )
    })
    @ApiResponse(responseCode = "409", description = "Proveedor existente con ese nombre", content = {
            @Content(
                    mediaType = "text/plain;charset=UTF-8",
                    schema = @Schema(example = "El proveedor con ese nombre ya existe en la base de datos")
            )
    })
    public ResponseEntity<ViewSupplierDTO> addSupplier(
            @RequestBody AddSupplierDTO supplier_DTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierService.createSupplier(supplier_DTO));
    }

    /**
     * Una pagina que contiene los datos de provedores.
     * <p>Se puede definir el tamaño con ?size=?</p>
     * <p>Se puede definir el numero de pagina con ?page=?</p>
     * <p>Se puede ordenar segun paramatro de objeto con ?sort=?</p>
     * @param paginacion Una pagina con contenido y informacion de pagina
     * @return Una pagina con contenido y informacion de pagina
     */
    @ApiResponse(
            responseCode = "200",
            description = "Encontrado",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ViewSupplierDTO.class)))
            })
    @ApiResponse(responseCode = "400", description = "Datos erroneos", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "No property 'companyNam' found for type 'Supplier'; Did you mean 'companyName'")
    ))
    @ApiResponse(responseCode = "404", description = "No encontrado", content = {
            @Content(
                    mediaType = "text/plain;charset=UTF-8",
                    schema = @Schema(example = "No hay proveedores")
            )
    })
    @Operation(summary = "Busca una pagina de proveedores", description = "Lista una pagina de proveedores")
    @GetMapping("/page")
    public ResponseEntity<Page<ViewSupplierDTO>> getSuppliers(
            @ParameterObject @PageableDefault(size = 10) Pageable paginacion
            )
    {
        return ResponseEntity.ok(supplierService.listSuppliers(paginacion));
    }

    //faltan anotaciones
    @GetMapping
    public ResponseEntity<List<ViewSupplierDTO>> getAllSuppliers() {
         return ResponseEntity.ok(supplierService.getAllSuppliers());
    }


    /**
     * Consigue un proveedor con su respectivo id
     * @param id El id del proveedor
     * @return Un DTO de proveedor
     */
    @ApiResponse(responseCode = "200", description = "Encontrado", content = {
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ViewSupplierDTO.class)
            )
    })
    @ApiResponse(responseCode = "404", description = "No encontrado", content = {
            @Content(
                    mediaType = "text/plain;charset=UTF-8",
                    schema = @Schema(example = "Proveedor no encontrado")
            )
    })
    @GetMapping("/{id}")
    @Operation(summary = "Busca un proveedor", description = "Busca un proveedor segun id")
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
    @ApiResponse(responseCode = "200", description = "Proveedor Actualizado", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ViewSupplierDTO.class)
    ))
    @ApiResponse(responseCode = "400", description = "Error en datos introducidos", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(defaultValue = "(Un mensaje de error mostrando los errores del usuario)")
    ))
    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "El proveedor no existe!!")
    ))

    @ApiResponse(responseCode = "409", description = "Datos de proveedor ya existentes", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(description = "Un mensaje de error para el usuario", examples = {"El proveedor con ese nombre ya existe en la base de datos", "El CUIT ingresado ya se encuentra registrado"})
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
    @ApiResponse(responseCode = "204", description = "Eliminado correctamente", content = @Content())
    @ApiResponse(responseCode = "404", description = "Proveedor No encontrado", content = @Content(
            mediaType = "text/plain;charset=UTF-8",
            schema = @Schema(example = "Proveedor no encontrado")
    ))
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un proveedor segun su id")
    public ResponseEntity<?> deleteSupplier(
            @Parameter(description = "El id del proveedor para eliminar <b>permanentemente</b>", example = "1")
            @PathVariable int id)
    {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}
