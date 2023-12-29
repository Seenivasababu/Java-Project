package com.example.JavaEcommerce.Service;

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
import com.example.JavaEcommerce.Entity.User;
import com.example.JavaEcommerce.Repository.CartRepository;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartService cartService;

    @Test
    void testAddToCart() throws Exception {
        // Mock user and product
        User mockUser = new User(1L, "TestUser", "test@example.com"); // Adjust based on your test data
        Product mockProduct = new Product(1L, "TestProduct", 19.99); // Adjust based on your test data

        // Mock the service methods
        when(userService.getUserById(1L)).thenReturn(Optional.of(mockUser));
        when(productService.getProductById(1L)).thenReturn(Optional.of(mockProduct));

        // Mock the repository save method
        when(cartRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Create a cart item
        Cart cartItem = new Cart(1L, 1L, 1L, 2); // Adjust based on your test data

        // Call the service method
        Cart result = cartService.addToCart(cartItem);

        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals(1L, result.getProductId());
        assertEquals(2, result.getQuantity());
    }

    @Test
    void testGetAllCartItemsForUser() {
        // Mock the repository method
        when(cartRepository.findAllByUserId(1L)).thenReturn(Collections.singletonList(new Cart()));

        // Call the service method
        List<Cart> cartItems = cartService.getAllCartItemsForUser(1L);

        // Assertions based on your test data
        assertNotNull(cartItems);
        assertEquals(1, cartItems.size()); // Adjust based on your test data
        // Add more assertions as needed
    }
    @Test
    void testUpdateCartItemQuantity() {
        // Mock the repository and service methods
        Cart mockCart = new Cart(1L, 1L, 1L, 2); // Adjust based on your test data
        when(cartRepository.findById(1L)).thenReturn(Optional.of(mockCart));
        when(cartRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the service method
        Optional<Cart> result = cartService.updateCartItemQuantity(1L, 3);

        assertTrue(result.isPresent());
        assertEquals(3, result.get().getQuantity());
    }

    @Test
    void testRemoveFromCart() {
        // Mock the repository method
        when(cartRepository.existsById(1L)).thenReturn(true);

        // Call the service method
        boolean result = cartService.removeFromCart(1L);

        assertTrue(result);
        verify(cartRepository, times(1)).deleteById(1L);
    }
}
