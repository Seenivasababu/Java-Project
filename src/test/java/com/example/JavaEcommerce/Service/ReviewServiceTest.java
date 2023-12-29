package com.example.JavaEcommerce.Service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.JavaEcommerce.Entity.Cart;
import com.example.JavaEcommerce.Entity.Product;
import com.example.JavaEcommerce.Entity.Review;
import com.example.JavaEcommerce.Entity.ReviewRequest;
import com.example.JavaEcommerce.Entity.User;
import com.example.JavaEcommerce.Repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void testCreateReview() {
        // Mock review request
        ReviewRequest reviewRequest = new ReviewRequest(1L, 1L, 4.5, "Great product"); // Adjust based on your test data

        // Mock user and product
        User mockUser = new User(1L, "TestUser", "test@example.com"); // Adjust based on your test data
        Product mockProduct = new Product(1L, "TestProduct", 19.99); // Adjust based on your test data

        // Mock the service methods
        when(userService.getUserById(1L)).thenReturn(Optional.of(mockUser));
        when(productService.getProductById(1L)).thenReturn(Optional.of(mockProduct));

        // Mock the repository save method
        when(reviewRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the service method
        Review result = reviewService.createReview(reviewRequest);

        assertNotNull(result);
        assertEquals(4.5, result.getRating(), 0.001); // Using delta for double comparison
        assertEquals("Great product", result.getComment());
        assertEquals(mockUser, result.getUser());
        assertEquals(mockProduct, result.getProduct());
    }

    @Test
    void testGetReviewById() {
        // Mock the repository method
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(new Review()));

        // Call the service method
        Optional<Review> review = reviewService.getReviewById(1L);

        assertTrue(review.isPresent());
        // Add more assertions as needed
    }

    @Test
    void testGetAllReviewsForProduct() {
        // Mock the repository method
        when(reviewRepository.findByProductId(1L)).thenReturn(Collections.singletonList(new Review()));

        // Call the service method
        List<Review> reviews = reviewService.getAllReviewsForProduct(1L);

        assertNotNull(reviews);
        // Add more assertions as needed
    }

    @Test
    void testUpdateReview() {
        // Mock the repository and service methods
        ReviewRequest updatedReviewRequest = new ReviewRequest(1L, 1L, 4.8, "Updated comment"); // Adjust based on your test data
        Review mockReview = new Review(1L, new User(), new Product(), 4.5, "Original comment"); // Adjust based on your test data
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(mockReview));
        when(reviewRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the service method
        Optional<Review> result = reviewService.updateReview(1L, updatedReviewRequest);

        assertTrue(result.isPresent());
        assertEquals(4.8, result.get().getRating(), 0.001); // Using delta for double comparison
        assertEquals("Updated comment", result.get().getComment());
    }

    @Test
    void testDeleteReview() {
        // Mock the repository method
        when(reviewRepository.existsById(1L)).thenReturn(true);

        // Call the service method
        boolean result = reviewService.deleteReview(1L);

        assertTrue(result);
        verify(reviewRepository, times(1)).deleteById(1L);
    }
}

