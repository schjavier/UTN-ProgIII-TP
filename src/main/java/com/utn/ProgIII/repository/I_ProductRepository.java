package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface I_ProductRepository extends JpaRepository<Product, Integer> {





}
