package com.example.JavaEcommerce.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JavaEcommerce.Entity.Cart;
import com.example.JavaEcommerce.Entity.Product;
import com.example.JavaEcommerce.Entity.User;
import com.example.JavaEcommerce.Repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    // Add a product to the cart
    public Cart addToCart(Cart cart) throws Exception {
        // Additional validation or business logic can be added here
        Optional<User> userOptional = userService.getUserById(cart.getUserId());
        Optional<Product> productOptional = productService.getProductById(cart.getProductId());

        if (userOptional.isPresent() && productOptional.isPresent()) {
            User user = userOptional.get();
            Product product = productOptional.get();

            
            return cartRepository.save(cart);
        } else {
            // Handle the case where user or product is not found
            throw new Exception("User or product not found");
        }
    }


    // Get all cart items for a user
    public List<Cart> getAllCartItemsForUser(Long userId) {
        return cartRepository.findAllByUserId(userId);
    }

    // Update quantity of a product in the cart
    public Optional<Cart> updateCartItemQuantity(Long cartId, int newQuantity) {
        // Additional validation or business logic can be added here
        return cartRepository.findById(cartId).map(cartItem -> {
            cartItem.setQuantity(newQuantity);
            return cartRepository.save(cartItem);
        });
    }

    // Remove a product from the cart
    public boolean removeFromCart(Long cartId) {
        // Additional validation or business logic can be added here
        if (cartRepository.existsById(cartId)) {
            cartRepository.deleteById(cartId);
            return true;
        }
        return false;
    }
}
