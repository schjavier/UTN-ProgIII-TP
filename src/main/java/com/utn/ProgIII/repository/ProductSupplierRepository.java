package com.utn.ProgIII.repository;

import com.utn.ProgIII.dto.ExtendedProductEmployeeDTO;
import com.utn.ProgIII.dto.ProductPriceSupplierEmployeeDTO;
import com.utn.ProgIII.dto.ProductPriceSupplierManagerDTO;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.dto.ExtendedProductManagerDTO;
import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import com.utn.ProgIII.model.Supplier.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, Long> {

    @Query("SELECT NEW com.utn.ProgIII.dto.ExtendedProductManagerDTO(" +
            "p.idProduct, p.name, ps.cost, ps.profitMargin, ps.price, ps.price/:dolarPrice) " +
            "FROM ProductSupplier ps JOIN ps.product p " +
            "WHERE ps.supplier.id = :idSupplier")
    List<ExtendedProductManagerDTO> productsBySupplierManager(@Param("idSupplier") Long idSupplier, @Param("dolarPrice") BigDecimal dollarPrice);

    @Query("SELECT NEW com.utn.ProgIII.dto.ExtendedProductEmployeeDTO(" +
            "p.idProduct, p.name, ps.price) " +
            "FROM ProductSupplier ps JOIN ps.product p " +
            "WHERE ps.supplier.id = :idSupplier")
    List<ExtendedProductEmployeeDTO> productsBySupplierEmployee(@Param("idSupplier") Long idSupplier);



    @Query("SELECT NEW com.utn.ProgIII.dto.ProductPriceSupplierManagerDTO(" +
            "s.idSupplier, s.companyName, ps.cost, ps.profitMargin, ps.price, ps.price/:dolarPrice) " +
            "FROM ProductSupplier ps JOIN ps.supplier s " +
            "WHERE ps.product.idProduct = :idProduct")
    List<ProductPriceSupplierManagerDTO> listPricesByProductManager(@Param("idProduct") Long idProduct, @Param("dolarPrice") BigDecimal dollarPrice);

    @Query("SELECT NEW com.utn.ProgIII.dto.ProductPriceSupplierEmployeeDTO(" +
            "s.idSupplier, s.companyName, ps.price) " +
            "FROM ProductSupplier ps JOIN ps.supplier s " +
            "WHERE ps.product.idProduct = :idProduct")
    List<ProductPriceSupplierEmployeeDTO> listPricesByProductEmployee(@Param("idProduct") Long idProduct);

    boolean existsByProductAndSupplier(Product product, Supplier supplier);

    ProductSupplier getByProductAndSupplier(Product product, Supplier supplier);

    void removeAllByProduct_IdProduct(Long productIdProduct);
}
