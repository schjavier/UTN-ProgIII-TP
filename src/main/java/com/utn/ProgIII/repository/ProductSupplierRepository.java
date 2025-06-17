package com.utn.ProgIII.repository;

import com.utn.ProgIII.dto.ExtendedProductToEmployeeDTO;
import com.utn.ProgIII.dto.SupplierProductListToEmployeeDTO;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.dto.ExtendedProductDTO;
import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import com.utn.ProgIII.model.Supplier.Supplier;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, Long> {

    @Query("SELECT NEW com.utn.ProgIII.dto.ExtendedProductDTO(" +
            "p.idProduct, p.name, ps.cost, ps.profitMargin, ps.price, p.status) " +
            "FROM ProductSupplier ps JOIN ps.product p " +
            "WHERE ps.supplier.id = :idSupplier")
    List<ExtendedProductDTO> productsBySupplier(@Param("idSupplier") Long idSupplier);

    boolean existsByProductAndSupplier(Product product, Supplier supplier);

    ProductSupplier getByProductAndSupplier(Product product, Supplier supplier);


    //nuevo
    @EntityGraph(attributePaths = {"product", "supplier"})
    @Query("SELECT ps FROM ProductSupplier ps WHERE ps.id = :id")
    Optional<ProductSupplier> findByIdWithRelations(@Param("id") Long id);

    @Query("SELECT NEW com.utn.ProgIII.dto.ExtendedProductToEmployeeDTO(" +
            "p.idProduct, p.name, ps.price) " +
            "FROM ProductSupplier ps JOIN ps.product p " +
            "WHERE ps.supplier.id = :idSupplier")
    List<ExtendedProductToEmployeeDTO> productsBySupplierToEmployee(@Param("idSupplier") Long idSupplier);

}
