package com.example.JavaEcommerce.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.JavaEcommerce.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom query methods if needed
	Page<User> findAll(Pageable pageable);
}
