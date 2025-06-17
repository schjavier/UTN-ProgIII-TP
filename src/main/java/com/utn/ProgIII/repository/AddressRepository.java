package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.Address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
