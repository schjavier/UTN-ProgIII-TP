package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import com.utn.ProgIII.service.implementations.SupplierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/supplier")
/**
 * <h1>Necesita documentacion de swagger</h1>
 */
public class SupplierController {

    @Autowired
    private SupplierServiceImpl supplierService;

    /**
     * Responde a peticiones http con la url "/supplier" usando POST
     * @param supplier_DTO Un DTO que representa el usuario cargado desde el frontend
     * @return Un usuario cargado desde el backend
     */
    @PostMapping
    public ResponseEntity<ViewSupplierDTO> addSupplier(@RequestBody AddSupplierDTO supplier_DTO)
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
    public ResponseEntity<List<ViewSupplierDTO>> getSuppliers(@PathVariable int page, @PathVariable int size)
    {
        return ResponseEntity.ok(supplierService.listSuppliers(page,size));
    }

    /**
     * Consigue un proveedor con su respectivo id
     * @param id El id del proveedor
     * @return Un DTO de proveedor
     */
    @GetMapping("/{id}")
    public ResponseEntity<ViewSupplierDTO> getSupplier(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.viewOneSupplier(id));
    }

    /**
     * Actualiza un proveedor existente con todos sus datos segun la id que tiene. Responde a peticiones http con la url "/{id}"
     * @param supplier_DTO Un objeto DTO que tiene todos los datos del proveedor
     * @param id El id del proveedor para modificar
     * @return Un DTO que muestra todo el proveedor con sus cambios.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplier(@RequestBody AddSupplierDTO supplier_DTO, @PathVariable Long id)
    {
        return ResponseEntity.ok(supplierService.updateSupplier(supplier_DTO,id));
    }

    /**
     * Elimina un proveedor de la base de datos. Responde a peticiones http con la url "/{id}"
     * @param id El id del proveedor para eliminar
     * @return Un booleano verdadero, en caso de exito.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteSupplier(@PathVariable int id)
    {
        return ResponseEntity.ok(supplierService.deleteSupplier(id));
    }
}
