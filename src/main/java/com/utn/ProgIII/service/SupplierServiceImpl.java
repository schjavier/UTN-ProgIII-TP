package com.utn.ProgIII.service;

import com.utn.ProgIII.dto.AddSupplierDTO;
import com.utn.ProgIII.exceptions.SupplierNotFoundException;
import com.utn.ProgIII.mapper.SupplierMapper;
import com.utn.ProgIII.model.Supplier.Supplier;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import com.utn.ProgIII.repository.AddressRepository;
import com.utn.ProgIII.repository.SupplierRepository;
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
        System.out.println(suppliermapper.toObjectFromAddSupplierDTO(supplierDTO).toString());
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
     * @param page El numero de la pagina (comienza en 0)
     * @param size El tamaño de la pagina
     * @return Una pagina de provedores
     */
    @Override
    public List<ViewSupplierDTO> listSuppliers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Supplier> query_page = supplierRepository.findAll(pageable);


        return query_page.stream().map(suppliermapper::toViewSupplierDTO).toList();
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

    @Override
    public ViewSupplierDTO modifySupplier(ViewSupplierDTO supplierDTO, long id) {
        return null;
    }



}
