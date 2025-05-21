package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface I_ProductRepository extends JpaRepository<Product, Integer> {

    //metodo propio buscar por nombre de producto
    @Query ("SELECT p FROM Product p WHERE p.name like '%:name%'")
    List<Product> getByname (@Param("name") String name);

}
