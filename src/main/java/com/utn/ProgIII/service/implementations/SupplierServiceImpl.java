package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.exceptions.InvalidRequestException;
import com.utn.ProgIII.exceptions.SupplierNotFoundException;
import com.utn.ProgIII.mapper.SupplierMapper;
import com.utn.ProgIII.model.Address.Address;
import com.utn.ProgIII.model.Supplier.Supplier;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import com.utn.ProgIII.repository.SupplierRepository;
import com.utn.ProgIII.service.interfaces.SupplierService;
import com.utn.ProgIII.validations.SupplierValidations;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierValidations supplierValidations;
    private final SupplierMapper suppliermapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierValidations supplierValidations, SupplierMapper suppliermapper) {
        this.supplierRepository = supplierRepository;
        this.supplierValidations = supplierValidations;
        this.suppliermapper = suppliermapper;
    }

    /**
     * El proveedor es agregado una vez de que se transforma el a un DTO
     * @param supplierDTO El objeto de transferencia recivido desde el frontend
     * @return Un objeto DTO para mostrar los datos como una respuesta frontend
     */
    @Override
    public ViewSupplierDTO createSupplier(AddSupplierDTO supplierDTO) {
        Supplier supplier = suppliermapper.toObjectFromAddSupplierDTO(supplierDTO);

        supplierValidations.validateCompanyNameNotExists(supplierDTO.companyName());
        supplierValidations.validateSupplierByCuit(supplier.getCuit());

        supplier = supplierRepository.save(supplier);
        return suppliermapper.toViewSupplierDTO(supplier);
    }

    /**
     * Devuelve un DTO de un proveedor que sea pedido.
     * @param id El id del proveedor.
     * @return {@link ViewSupplierDTO} un objeto para ver el resultado.
     */
    @Override
    public ViewSupplierDTO viewOneSupplier(long id) {
        Supplier sup = supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Proveedor no encontrado"));

        return suppliermapper.toViewSupplierDTO(sup);
    }

    /**
     * Una pagina que contiene los datos de provedores.
     * <p>Se puede definir el tamaño con ?size=?</p>
     * <p>Se puede definir el numero de pagina con ?page=?</p>
     * <p>Se puede ordenar segun paramatro de objeto con ?sort=?</p>
     * @param paginacion Una pagina con contenido y informacion de pagina
     * @return Una pagina con contenido y informacion de pagina
     */
    public Page<ViewSupplierDTO> listSuppliers(Pageable paginacion)
    {
        Page<ViewSupplierDTO> page = supplierRepository.findAll(paginacion).map(suppliermapper::toViewSupplierDTO);

        if(page.getNumberOfElements() == 0)
        {
            throw new SupplierNotFoundException("No hay proveedores");
        }

        return page;
    }

    @Override
    public List<ViewSupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(suppliermapper::toViewSupplierDTO)
                .collect(Collectors.toList());
    }

    /**
     * Elimina un proveedor en caso de que exista
     * @param id El id del proveedor
     * @return un booleano representando el exito.
     */
    @Override
    public void deleteSupplier(long id) {
        if(supplierRepository.existsById(id))
        {
            supplierRepository.deleteById(id);
        } else {
            throw new SupplierNotFoundException("Ese proveedor no existe");
        }
    }

    /**
     * Modifica un proveedor segun los datos enviados
     * @param supplierDTO
     * @return
     */
    @Override
    @Transactional
    public ViewSupplierDTO updateSupplier(AddSupplierDTO supplierDTO, Long id) {
        Supplier supplier_to_update = supplierRepository.findById(id).orElseThrow(() -> new SupplierNotFoundException("El proveedor no existe!!"));

        supplierValidations.validateModifiedCompanyNameNotExists(supplier_to_update.getCompanyName(),
                supplierDTO.companyName());
        supplierValidations.validateModifiedSupplierByCuit(supplier_to_update.getCuit(),supplierDTO.cuit());

        supplier_to_update.setCompanyName(supplierDTO.companyName());
        supplier_to_update.setCuit(supplierDTO.cuit());
        supplier_to_update.setPhoneNumber(supplierDTO.phoneNumber());
        supplier_to_update.setEmail(supplierDTO.email());

        Address address = supplier_to_update.getAddress();

        address.setStreet(supplierDTO.address().street());
        address.setNumber(supplierDTO.address().number());
        address.setCity(supplierDTO.address().city());

        supplier_to_update.setAddress(address);

        return suppliermapper.toViewSupplierDTO(supplierRepository.save(supplier_to_update));
    }



}
