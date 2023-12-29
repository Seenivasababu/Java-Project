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

import com.example.JavaEcommerce.Entity.Cart;
import com.example.JavaEcommerce.Service.CartService;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @Test
    void testAddToCart() throws Exception {
        // Mock cart
        Cart mockCart = new Cart(1L, 1L, 1L, 2); // Adjust based on your test data

        // Mock the service method
        when(cartService.addToCart(any())).thenReturn(mockCart);

        // Call the controller method
        ResponseEntity<Cart> responseEntity = cartController.addToCart(mockCart);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockCart, responseEntity.getBody());
    }

    @Test
    void testGetAllCartItemsForUser() {
        // Mock cart items
        List<Cart> mockCartItems = new ArrayList<>(); // Adjust based on your test data

        // Mock the service method
        when(cartService.getAllCartItemsForUser(1L)).thenReturn(mockCartItems);

        // Call the controller method
        ResponseEntity<List<Cart>> responseEntity = cartController.getAllCartItemsForUser(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockCartItems, responseEntity.getBody());
    }

    @Test
    void testUpdateCartItemQuantity() {
        // Mock updated cart item
        Cart mockUpdatedCartItem = new Cart(1L, 1L, 1L, 3); // Adjust based on your test data

        // Mock the service method
        when(cartService.updateCartItemQuantity(1L, 3)).thenReturn(Optional.of(mockUpdatedCartItem));

        // Call the controller method
        ResponseEntity<Cart> responseEntity = cartController.updateCartItemQuantity(1L, 3);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockUpdatedCartItem, responseEntity.getBody());
    }

    @Test
    void testUpdateCartItemQuantityNotFound() {
        // Mock the service method returning an empty Optional
        when(cartService.updateCartItemQuantity(1L, 3)).thenReturn(Optional.empty());

        // Call the controller method
        ResponseEntity<Cart> responseEntity = cartController.updateCartItemQuantity(1L, 3);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testRemoveFromCart() {
        // Mock the service method returning true (indicating successful removal)
        when(cartService.removeFromCart(1L)).thenReturn(true);

        // Call the controller method
        ResponseEntity<Void> responseEntity = cartController.removeFromCart(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void testRemoveFromCartNotFound() {
        // Mock the service method returning false (indicating cart item not found)
        when(cartService.removeFromCart(1L)).thenReturn(false);

        // Call the controller method
        ResponseEntity<Void> responseEntity = cartController.removeFromCart(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
