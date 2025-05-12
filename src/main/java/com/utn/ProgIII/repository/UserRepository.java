package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
