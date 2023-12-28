package com.example.JavaEcommerce.Controller;
import com.example.JavaEcommerce.Entity.Cart;
import com.example.JavaEcommerce.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    // API to add a product to the cart
    @PostMapping
    public ResponseEntity<Cart> addToCart(@RequestBody Cart cart) {
        try {
            Cart addedToCart = cartService.addToCart(cart);
            return new ResponseEntity<>(addedToCart, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle the exception, e.g., return a proper error response
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // API to get all cart items for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cart>> getAllCartItemsForUser(@PathVariable Long userId) {
        List<Cart> cartItems = cartService.getAllCartItemsForUser(userId);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    // API to update quantity of a product in the cart
    @PutMapping("/{cartId}")
    public ResponseEntity<Cart> updateCartItemQuantity(
            @PathVariable Long cartId,
            @RequestParam int quantity) {
        Optional<Cart> updatedCartItem = cartService.updateCartItemQuantity(cartId, quantity);
        return updatedCartItem.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // API to remove a product from the cart
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartId) {
        boolean removed = cartService.removeFromCart(cartId);
        return removed ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
