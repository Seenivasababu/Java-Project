package com.example.JavaEcommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JavaEcommerce.Entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Find all cart items for a user by user ID
    List<Cart> findAllByUserId(Long userId);
}
