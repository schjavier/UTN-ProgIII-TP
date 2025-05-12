package com.utn.ProgIII.controller;

import com.utn.ProgIII.model.Supplier.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/supplier")
@Controller
public class SupplierController {

    @Autowired
    private SupplierServices supplierService;

    // tengo que ver si todo esto esta bien...
    @PostMapping
    public ResponseEntity<AddSupplierDTO> addSupplier(@RequestBody AddSupplierDTO supplier_DTO)
    {
        return ResponseEntity.ok(supplierService.createSupplier(supplier_DTO));
    }

    @GetMapping("/page{page}/{size}")
    public ResponseEntity<Page<Supplier>> getSuppliers(@PathVariable int page, @PathVariable int size)
    {
        return ResponseEntity.ok(supplierService.listSuppliers(page,size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewSupplierDTO> getSupplier(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.viewOneSupplier(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> changeSupplier(@PathVariable Long id)
    {
        // todavia no hay nada
    }
}

    //@DeleteMapping("/")

