package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.ProductSupplier.dto.ExtendedProductDTO;
import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, Long> {

    @Query("SELECT NEW com.utn.ProgIII.model.ProductSupplier.dto.ExtendedProductDTO(" +
            "p.idProduct, p.name, ps.cost, ps.profitMargin, ps.price, p.status) " +
            "FROM ProductSupplier ps JOIN ps.product p " +
            "WHERE ps.supplier.id = :idSupplier")
    List<ExtendedProductDTO> productsBySupplier(@Param("idSupplier") Long idSupplier);

}
