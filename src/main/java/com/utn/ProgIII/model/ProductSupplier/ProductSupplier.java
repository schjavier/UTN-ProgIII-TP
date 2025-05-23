package com.utn.ProgIII.model.ProductSupplier;

import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Supplier.Supplier;
import jakarta.persistence.*;
import lombok.*;

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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_supplier")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    private Product product;

    private Float cost;

    private Float profitMargin;

    private Float price;



}
