package com.utn.ProgIII.model.Product;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@NotBlank

public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idProduct;

//    @ManyToMany(mappedBy = "idSupplier")
//    @JoinTable(name = "supplier")
//    @JoinColumn(name = "idSupplier",referencedColumnName = "idSupplier")
//    private int idSupplier;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener mas de 50 caracteres")
    private String name;

    @NotBlank(message = "El costo no puede ser nulo")
    @Positive(message = "El costo no puede ser menor a 0")
    private BigDecimal cost;

    @NotBlank(message = "El margen dee ganancia no puede ser nulo")
    @Positive(message = "El margen de ganancia debe ser superior a 0")
    private BigDecimal profitMargin;

    //@NotBlank(message = "El precio de venta no puede ser nulo")
    @Positive(message = "El precio de venta debe ser superiro a 0")
    private BigDecimal price;

    public Product(String name, BigDecimal cost, BigDecimal profitMargin, BigDecimal price) {
        this.name = name;
        this.cost = cost;
        this.profitMargin = profitMargin;
        this.price = price;
    }
}
