package com.example.JavaEcommerce.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.JavaEcommerce.Entity.Review;
import com.example.JavaEcommerce.Entity.ReviewRequest;
import com.example.JavaEcommerce.Service.ReviewService;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @Test
    void testCreateReview() {
        // Mock review request
        ReviewRequest mockReviewRequest = new ReviewRequest(1L,1L, 5, "Great product"); // Adjust based on your test data

        // Mock the service method
        when(reviewService.createReview(mockReviewRequest)).thenReturn(new Review()); // Adjust based on your test data

        // Call the controller method
        ResponseEntity<Review> responseEntity = reviewController.createReview(mockReviewRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testCreateReviewBadRequest() {
        // Mock review request
        ReviewRequest mockReviewRequest = new ReviewRequest(); // This could be an invalid request

        // Mock the service method to throw an exception
        when(reviewService.createReview(mockReviewRequest)).thenThrow(new IllegalArgumentException());

        // Call the controller method
        ResponseEntity<Review> responseEntity = reviewController.createReview(mockReviewRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testGetReviewById() {
        // Mock review
        Review mockReview = new Review(); // Adjust based on your test data

        // Mock the service method
        when(reviewService.getReviewById(1L)).thenReturn(Optional.of(mockReview));

        // Call the controller method
        ResponseEntity<Review> responseEntity = reviewController.getReviewById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testGetReviewByIdNotFound() {
        // Mock the service method returning an empty Optional
        when(reviewService.getReviewById(1L)).thenReturn(Optional.empty());

        // Call the controller method
        ResponseEntity<Review> responseEntity = reviewController.getReviewById(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    // Similar tests for other controller methods (getAllReviewsForProduct, updateReview, deleteReview)...

    // ...

}
