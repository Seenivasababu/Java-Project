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

import com.example.JavaEcommerce.Entity.Product;
import com.example.JavaEcommerce.Service.ProductService;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void testCreateProduct() {
        // Mock product
        Product mockProduct = new Product(1L, "TestProduct", 19.99); // Adjust based on your test data

        // Mock the service method
        when(productService.createProduct(any())).thenReturn(mockProduct);

        // Call the controller method
        ResponseEntity<Product> responseEntity = productController.createProduct(mockProduct);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockProduct, responseEntity.getBody());
    }

    @Test
    void testGetAllProducts() {
        // Mock products
        List<Product> mockProducts = new ArrayList<>(); // Adjust based on your test data

        // Mock the service method
        when(productService.getAllProducts()).thenReturn(mockProducts);

        // Call the controller method
        ResponseEntity<List<Product>> responseEntity = productController.getAllProducts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockProducts, responseEntity.getBody());
    }

    @Test
    void testGetProductById() {
        // Mock product
        Product mockProduct = new Product(1L, "TestProduct", 19.99); // Adjust based on your test data

        // Mock the service method
        when(productService.getProductById(1L)).thenReturn(Optional.of(mockProduct));

        // Call the controller method
        ResponseEntity<Product> responseEntity = productController.getProductById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockProduct, responseEntity.getBody());
    }

    @Test
    void testGetProductByIdNotFound() {
        // Mock the service method returning an empty Optional
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        // Call the controller method
        ResponseEntity<Product> responseEntity = productController.getProductById(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testUpdateProduct() {
        // Mock updated product
        Product mockUpdatedProduct = new Product(1L, "UpdatedProduct", 29.99); // Adjust based on your test data

        // Mock the service method
        when(productService.updateProduct(1L, mockUpdatedProduct)).thenReturn(Optional.of(mockUpdatedProduct));

        // Call the controller method
        ResponseEntity<Product> responseEntity = productController.updateProduct(1L, mockUpdatedProduct);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockUpdatedProduct, responseEntity.getBody());
    }

    @Test
    void testUpdateProductNotFound() {
        // Mock the service method returning an empty Optional
    	Product product = new Product();
        when(productService.updateProduct(eq(1L), eq(product))).thenReturn(Optional.empty());

        // Call the controller method
        ResponseEntity<Product> responseEntity = productController.updateProduct(1L, product);

        assertFalse(responseEntity.getStatusCode().is2xxSuccessful());
        assertNull(responseEntity.getBody());
    }


    @Test
    void testDeleteProduct() {
        // Mock the service method returning true (indicating successful deletion)
        when(productService.deleteProduct(1L)).thenReturn(true);

        // Call the controller method
        ResponseEntity<Void> responseEntity = productController.deleteProduct(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteProductNotFound() {
        // Mock the service method returning false (indicating product not found)
        when(productService.deleteProduct(1L)).thenReturn(false);

        // Call the controller method
        ResponseEntity<Void> responseEntity = productController.deleteProduct(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
