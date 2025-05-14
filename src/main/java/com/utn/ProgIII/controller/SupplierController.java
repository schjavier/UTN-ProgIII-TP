package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import com.utn.ProgIII.model.Supplier.*;
import com.utn.ProgIII.service.SupplierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/supplier")
@Controller
public class SupplierController {

    @Autowired
    private SupplierServiceImpl supplierService;

    // tengo que ver si todo esto esta bien...
    @PostMapping
    public ResponseEntity<ViewSupplierDTO> addSupplier(@RequestBody AddSupplierDTO supplier_DTO) // Field 'idSupplier' doesn't have a default value?
    {
        return ResponseEntity.ok(supplierService.createSupplier(supplier_DTO));
    }

    @GetMapping("/page{page}/{size}") // No funciona Cannot invoke "com.utn.ProgIII.model.Address.Address.getIdaddress()" because the return value of "com.utn.ProgIII.model.Supplier.Supplier.getAddress()" is null
    public ResponseEntity<List<ViewSupplierDTO>> getSuppliers(@PathVariable int page, @PathVariable int size)
    {
        return ResponseEntity.ok(supplierService.listSuppliers(page,size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewSupplierDTO> getSupplier(@PathVariable Long id) { // No funciona Cannot invoke "com.utn.ProgIII.model.Address.Address.getIdaddress()" because the return value of "com.utn.ProgIII.model.Supplier.Supplier.getAddress()" is null
        return ResponseEntity.ok(supplierService.viewOneSupplier(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> changeSupplier(@PathVariable Long id)
    {
        return null;// todavia no hay nada
    }



    @DeleteMapping("/{id}") // funciona
    public ResponseEntity<Boolean> deleteSupplier(@PathVariable int id)
    {
        return ResponseEntity.ok(supplierService.deleteSupplier(id));
    }
}
