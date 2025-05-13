package com.utn.ProgIII.service;

import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.model.Supplier.Supplier;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import com.utn.ProgIII.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    // es algo asi? o entendi mal?
    @Override
    public AddSupplierDTO createSupplier(AddSupplierDTO supplierDTO) {
        Supplier supplier_toadd = new Supplier();

        supplier_toadd.setCompanyName(supplierDTO.companyName());
        supplier_toadd.setCuit(supplierDTO.cuit());
        supplier_toadd.setPhoneNumber(supplierDTO.phoneNumber());
        supplier_toadd.setEmail(supplierDTO.email());
        supplier_toadd.setAddress(supplierDTO.address());
        Supplier sup = supplierRepository.save(supplier_toadd);


        return null;// hacer mapper;
    }

    @Override
    public ViewSupplierDTO viewOneSupplier(long id) {
        Optional<Supplier> supplier_op = supplierRepository.findById(id);
        // en caso de que no esté como lanzo una exception?

        // deberia tener un check de if present para ver si está, si no se deberia tirar un error (o no?)
        return new ViewSupplierDTO(
                supplier_op.get().getIdSupplier(),
                supplier_op.get().getCompanyName(),
                supplier_op.get().getCuit(),
                supplier_op.get().getPhoneNumber(),
                supplier_op.get().getEmail(),
                supplier_op.get().getAddress()
        );
    }

    @Override
    // esto esta bastante bueno...
    public Page<Supplier> listSuppliers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return supplierRepository.findAll(pageable);
    }


    @Override
    public boolean deleteSupplier(long id) {
        return false;
    }

    @Override
    public ViewSupplierDTO modifySupplier(ViewSupplierDTO supplierDTO, long id) {
        return null;
    }



}
