package com.utn.ProgIII.controller;

import com.utn.ProgIII.model.ProductSupplier.dto.CreateProductSupplierDTO;
import com.utn.ProgIII.model.ProductSupplier.dto.ResponseProductSupplierDTO;
import com.utn.ProgIII.model.ProductSupplier.dto.SupplierProductListDTO;
import com.utn.ProgIII.model.ProductSupplier.dto.UpdateProductSupplierDTO;
import com.utn.ProgIII.service.interfaces.ProductSupplierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productSupplier")
public class ProductSupplierController {

    private final ProductSupplierService productSupplierService;

    public ProductSupplierController(ProductSupplierService productSupplierService){
        this.productSupplierService = productSupplierService;
    }

    @PostMapping
    public ResponseEntity<ResponseProductSupplierDTO> createProductSupplier(@Valid @RequestBody CreateProductSupplierDTO request){

        ResponseProductSupplierDTO response = productSupplierService.createProductSupplier(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseProductSupplierDTO> modifyProductSupplier(@Valid @RequestBody UpdateProductSupplierDTO request,
                                                                            @PathVariable Long id){

        ResponseProductSupplierDTO response = productSupplierService.updateProductSupplier(request, id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/filter/{companyName}")
    public ResponseEntity<SupplierProductListDTO> listAllProductsBySupplier(@PathVariable String companyName){

        SupplierProductListDTO response = productSupplierService.listProductsBySupplier(companyName);

        return ResponseEntity.ok(response);

    }


}
