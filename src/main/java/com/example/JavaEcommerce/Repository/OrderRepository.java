package com.example.JavaEcommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JavaEcommerce.Entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Custom query method to find orders by user ID
    List<Order> findByUserId(Long userId);
}
