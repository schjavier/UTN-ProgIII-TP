package com.utn.ProgIII.model.ProductSupplier;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Supplier.Supplier;
import jakarta.persistence.*;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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

    @NotNull(message = "El costo no puede estas vacío")
    @Positive(message = "El costo no puede ser negativo")
    private BigDecimal cost;

    @NotNull(message = "El margen de ganancia no puede estar vacio")
    @PositiveOrZero(message = "El margen de ganancias tiene que ser mayor a cero")
    private BigDecimal profitMargin;

    @Positive(message = "El precio no puede ser menor a cero.")
    private BigDecimal price;

    public ProductSupplier(Supplier supplier, Product product, BigDecimal cost, BigDecimal profitMargin){
        this.supplier = supplier;
        this.product = product;
        this.cost = cost;
        this.profitMargin = profitMargin;
        this.price = cost.add(cost.multiply(profitMargin).divide(BigDecimal.valueOf(100), RoundingMode.CEILING));
    }

}
