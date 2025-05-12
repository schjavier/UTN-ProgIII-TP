package com.utn.ProgIII.controller;

import com.utn.ProgIII.model.Supplier.AddSupplierDTO;
import com.utn.ProgIII.model.Supplier.Supplier;
import com.utn.ProgIII.model.Supplier.SupplierRepository;
import com.utn.ProgIII.model.Supplier.ViewSupplierDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/supplier")
@Controller
public class SupplierController {
    @Autowired
    private SupplierRepository supplierRepository;

    @PostMapping
    public void addSupplier(@RequestBody AddSupplierDTO supplier_DTO)
    {
        /* esto tendrndria que estar en el service
        Supplier supplier = new Supplier();
        supplier.setCompanyName(supplier_DTO.companyName());
        supplier.setCuit(supplier_DTO.cuit());
        supplier.setPhoneNumber(supplier_DTO.phoneNumber());
        supplier.setEmail(supplier_DTO.email());
        supplier.setAddress(supplier_DTO.address());
        */
        //supplierRepository.save(supplier);
    }

    @GetMapping
    public List<Supplier> getSuppliers()
    {
        // del servicio que trae el DTO... supplieretcetc
        return supplierRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewSupplierDTO> getSupplier(@PathVariable Long id) {
        // del servicio que trae el DTO... supplierRepository.findById(id);
        return ResponseEntity.ok();
    }

    @PutMapping("/{id}")
    public ResponseEntity changeSupplier(@PathVariable Long id)
    {
        supplierRepository.
    }
}

    //@DeleteMapping("/")

