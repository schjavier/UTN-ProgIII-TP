package com.utn.ProgIII.model.ProductSupplier;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Supplier.Supplier;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "product_supplier")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProductSupplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProductSupplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_supplier")
    @ToString.Exclude
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    @ToString.Exclude
    private Product product;

    private BigDecimal cost;

    private BigDecimal profitMargin;

    private BigDecimal price;

    public ProductSupplier(Supplier supplier, Product product, BigDecimal cost, BigDecimal profitMargin){
        this.supplier = supplier;
        this.product = product;
        this.cost = cost;
        this.profitMargin = profitMargin;
        this.price = cost.add(cost.multiply(profitMargin).divide(BigDecimal.valueOf(100), RoundingMode.CEILING));
    }

}
