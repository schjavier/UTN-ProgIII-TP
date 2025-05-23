package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.model.ProductSupplier.CreateProductSupplierDTO;
import com.utn.ProgIII.model.ProductSupplier.DatosProductSupplierDTO;
import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import com.utn.ProgIII.repository.ProductSupplierRepository;
import com.utn.ProgIII.service.interfaces.ProductSupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductSupplierServiceImpl implements ProductSupplierService {

    private final ProductSupplierRepository productSupplierRepository;

    public ProductSupplierServiceImpl(ProductSupplierRepository productSupplierRepository){
        this.productSupplierRepository = productSupplierRepository;
    }


    @Override
    public DatosProductSupplierDTO createProductSupplier(CreateProductSupplierDTO createProductSupplierDTO) {

        ProductSupplier productSupplier = new ProductSupplier();


        return null;
    }

    @Override
    public DatosProductSupplierDTO getOneProductSupplier(Long id) {
        return null;
    }

    @Override
    public Page<DatosProductSupplierDTO> getAllProductsSupplier(Pageable pagination) {
        return null;
    }

    @Override
    public boolean deleteProductSupplier(Long id) {
        return false;
    }
}
