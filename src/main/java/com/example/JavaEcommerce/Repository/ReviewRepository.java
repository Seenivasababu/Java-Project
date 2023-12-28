package com.example.JavaEcommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JavaEcommerce.Entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Custom query method to find reviews by product ID
    List<Review> findByProductId(Long productId);
}
