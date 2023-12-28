package com.example.JavaEcommerce.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.JavaEcommerce.Entity.Review;
import com.example.JavaEcommerce.Entity.ReviewRequest;
import com.example.JavaEcommerce.Service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;  // Assuming there is a ReviewService class

    // API to create a new review
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest reviewRequest) {
        try {
            Review createdReview = reviewService.createReview(reviewRequest);
            return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle the exception, e.g., return a proper error response
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // API to get details of a specific review
    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        Optional<Review> review = reviewService.getReviewById(reviewId);
        return review.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // API to get all reviews for a product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getAllReviewsForProduct(@PathVariable Long productId) {
        List<Review> productReviews = reviewService.getAllReviewsForProduct(productId);
        return new ResponseEntity<>(productReviews, HttpStatus.OK);
    }

    // API to update a review
    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewRequest reviewRequest) {
        Optional<Review> updatedReview = reviewService.updateReview(reviewId, reviewRequest);
        return updatedReview.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // API to delete a review
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        boolean deleted = reviewService.deleteReview(reviewId);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
