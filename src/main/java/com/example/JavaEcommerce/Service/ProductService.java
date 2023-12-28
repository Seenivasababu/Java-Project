package com.example.JavaEcommerce.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JavaEcommerce.Entity.Product;
import com.example.JavaEcommerce.Repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Create a new product
    public Product createProduct(Product product) {
        // Additional validation or business logic can be added here
        return productRepository.save(product);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get a specific product by ID
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    // Update a product by ID
    public Optional<Product> updateProduct(Long productId, Product updatedProduct) {
        // Additional validation or business logic can be added here
        return productRepository.findById(productId).map(product -> {
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            // Update other fields as needed
            return productRepository.save(product);
        });
    }

    // Delete a product by ID
    public boolean deleteProduct(Long productId) {
        // Additional validation or business logic can be added here
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }
}
