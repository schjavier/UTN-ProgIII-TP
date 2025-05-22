package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findByName (String name);
}
