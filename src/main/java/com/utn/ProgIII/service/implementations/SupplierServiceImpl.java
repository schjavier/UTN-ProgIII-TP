package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.exceptions.InvalidRequestException;
import com.utn.ProgIII.exceptions.SupplierNotFoundException;
import com.utn.ProgIII.mapper.SupplierMapper;
import com.utn.ProgIII.model.Address.Address;
import com.utn.ProgIII.model.Supplier.Supplier;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import com.utn.ProgIII.repository.AddressRepository;
import com.utn.ProgIII.repository.SupplierRepository;
import com.utn.ProgIII.service.interfaces.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;
    private AddressRepository addressRepository;

    @Autowired
    private SupplierMapper suppliermapper;


    /**
     * El provedor es agregado una vez de que se transforma el a un DTO
     * @param supplierDTO El objeto de transferencia recivido desde el frontend
     * @return Un objeto DTO para mostrar los datos como una respuesta frontend
     */
    @Override
    public ViewSupplierDTO createSupplier(AddSupplierDTO supplierDTO) {
        Supplier sup = supplierRepository.save(suppliermapper.toObjectFromAddSupplierDTO(supplierDTO));
        return suppliermapper.toViewSupplierDTO(sup);// hacer mapper;
    }

    /**
     * Devuelve un DTO de un provedor que sea pedido.
     * @param id El id del provedor.
     * @return {@link ViewSupplierDTO} un objeto para ver el resultado.
     */
    @Override
    public ViewSupplierDTO viewOneSupplier(long id) {
        Supplier sup = supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Provedor no encontrado!"));

        return suppliermapper.toViewSupplierDTO(sup);
    }

    /**
     * Devuelve una "pagina" de los provedores segun la posicion y tamaño de una pagina
     *
     * @param page El numero de la pagina (comienza en 1)
     * @param size El tamaño de la pagina
     * @return Una pagina de provedores
     */
    @Override
    public List<ViewSupplierDTO> listSuppliers(int page, int size) {
        page = page - 1;

        if(page < 0 || size < 1)
        {
            throw new InvalidRequestException("El tamaño o numero de la pagina es invalido!");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Supplier> query_page = supplierRepository.findAll(pageable);


        if(query_page.isEmpty())
        {
            throw new SupplierNotFoundException("No hay provedores!");
        }

        return query_page.stream().map(suppliermapper::toViewSupplierDTO).toList();
    }

    /**
     * Retorna todos los provedores
     * @return Lista de DTOs de provedores
     */
    @Override
    public List<ViewSupplierDTO> listAllSuppliers() {
        List<ViewSupplierDTO> supplier_list = supplierRepository.findAll().stream().map(suppliermapper::toViewSupplierDTO).toList();

        if(supplier_list.isEmpty())
        {
            throw new SupplierNotFoundException("No hay provedores!");
        }

        return supplier_list;
    }

    /**
     * Elimina un provedor en caso de que exista
     * @param id El id del provedor
     * @return un booleano representando el exito.
     */
    @Override
    public boolean deleteSupplier(long id) {
        boolean response = false;

        if(supplierRepository.existsById(id))
        {
            supplierRepository.deleteById(id);
            response = true;
        } else {
            throw new SupplierNotFoundException("Ese provedor no existe!");
        }

        return response;
    }

    /**
     * Modifica un provedor segun los datos enviados
     * @param supplierDTO
     * @return
     */
    @Override
    public ViewSupplierDTO modifySupplier(AddSupplierDTO supplierDTO, Long id) {
        Supplier supplier_to_update = supplierRepository.findById(id).orElseThrow(() -> new SupplierNotFoundException("El provedor no existe!!"));

        supplier_to_update.setCompanyName(supplierDTO.companyName());
        supplier_to_update.setCuit(supplierDTO.cuit());
        supplier_to_update.setPhoneNumber(supplierDTO.phoneNumber());
        supplier_to_update.setEmail(supplier_to_update.getEmail());

        Address address = supplier_to_update.getAddress();

        address.setStreet(supplierDTO.address().street());
        address.setNumber(supplierDTO.address().number());
        address.setCity(supplierDTO.address().city());

        supplier_to_update.setAddress(address);

        return suppliermapper.toViewSupplierDTO(supplierRepository.save(supplier_to_update));
    }



}
