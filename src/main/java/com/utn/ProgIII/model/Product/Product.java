package com.utn.ProgIII.model.Product;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@NotBlank

public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idProduct;

    @OneToMany(mappedBy = "idSupplier", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idSupplier",referencedColumnName = "idSupplier")
    private int idSupplier;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener mas de 50 caracteres")
    private String name;

    @NotBlank(message = "El costo no puede ser nulo")
    @Positive(message = "El costo no puede ser menor a 0")
    private double cost;

    @NotBlank(message = "El margen dee ganancia no puede ser nulo")
    @Positive(message = "El margen de ganancia debe ser superior a 0")
    private double profitMargin;

    //@NotBlank(message = "El precio de venta no puede ser nulo")
    @Positive(message = "El precio de venta debe ser superiro a 0")
    private double price;

    public Product(String name, double cost, double profitMargin, double price) {
        this.name = name;
        this.cost = cost;
        this.profitMargin = profitMargin;
        this.price = price;
    }
}
