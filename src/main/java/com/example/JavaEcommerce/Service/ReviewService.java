package com.example.JavaEcommerce.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JavaEcommerce.Entity.Product;
import com.example.JavaEcommerce.Entity.Review;
import com.example.JavaEcommerce.Entity.ReviewRequest;
import com.example.JavaEcommerce.Entity.User;
import com.example.JavaEcommerce.Repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;  // Assuming there is a ReviewRepository class

    @Autowired
    private UserService userService;  // Assuming there is a UserService class for user-related operations

    @Autowired
    private ProductService productService;  // Assuming there is a ProductService class for product-related operations

    public Review createReview(ReviewRequest reviewRequest) {
        // Perform validation or business logic if needed
        User user = userService.getUserById(reviewRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Product product = productService.getProductById(reviewRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Review review = new Review(user, product, reviewRequest.getRating(), reviewRequest.getComment());
        return reviewRepository.save(review);
    }

    public Optional<Review> getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId);
    }

    public List<Review> getAllReviewsForProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public Optional<Review> updateReview(Long reviewId, ReviewRequest reviewRequest) {
        return reviewRepository.findById(reviewId).map(review -> {
            review.setRating(reviewRequest.getRating());
            review.setComment(reviewRequest.getComment());
            return reviewRepository.save(review);
        });
    }

    public boolean deleteReview(Long reviewId) {
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }
}

