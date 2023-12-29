package com.example.JavaEcommerce.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.JavaEcommerce.Entity.Product;
import com.example.JavaEcommerce.Repository.ProductRepository;
import com.example.JavaEcommerce.Service.ProductService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testCreateProduct() {
        Product mockProduct = new Product(1L, "Test Product", 19.99); // You might need to adjust the constructor based on your actual implementation
        when(productRepository.save(any())).thenReturn(mockProduct);

        Product createdProduct = productService.createProduct(new Product());
        
        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getName());
        assertEquals(19.99, createdProduct.getPrice(), 0.001); // Using delta for double comparison
    }

    @Test
    void testGetAllProducts() {
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1L, "Product 1", 29.99));
        mockProducts.add(new Product(2L, "Product 2", 39.99));
        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> allProducts = productService.getAllProducts();

        assertNotNull(allProducts);
        assertEquals(2, allProducts.size());
    }

    @Test
    void testGetProductById() {
        Product mockProduct = new Product(1L, "Test Product", 19.99);
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        Optional<Product> product = productService.getProductById(1L);

        assertTrue(product.isPresent());
        assertEquals("Test Product", product.get().getName());
    }

    @Test
    void testUpdateProduct() {
        Product existingProduct = new Product(1L, "Existing Product", 29.99);
        Product updatedProduct = new Product(1L, "Updated Product", 39.99);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any())).thenReturn(updatedProduct);

        Optional<Product> result = productService.updateProduct(1L, updatedProduct);

        assertTrue(result.isPresent());
        assertEquals("Updated Product", result.get().getName());
        assertEquals(39.99, result.get().getPrice(), 0.001);
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);

        boolean result = productService.deleteProduct(1L);

        assertTrue(result);
        verify(productRepository, times(1)).deleteById(1L);
    }
}
