package com.example.JavaEcommerce.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.JavaEcommerce.Entity.Order;
import com.example.JavaEcommerce.Entity.User;
import com.example.JavaEcommerce.Service.OrderService;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void testCreateOrder() {
        // Mock order
        Order mockOrder = new Order(1L, new User(), "Pending", new Date()); // Adjust based on your test data

        // Mock the service method
        when(orderService.createOrder(1L, "Pending")).thenReturn(mockOrder);

        // Call the controller method
        ResponseEntity<Order> responseEntity = orderController.createOrder(1L, "Pending");

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockOrder, responseEntity.getBody());
    }

    @Test
    void testCreateOrderBadRequest() {
        // Mock the service method throwing an exception
        when(orderService.createOrder(anyLong(), anyString())).thenThrow(new IllegalArgumentException("User not found"));

        // Call the controller method
        ResponseEntity<Order> responseEntity = orderController.createOrder(1L, "Pending");

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testGetOrderById() {
        // Mock order
        Order mockOrder = new Order(1L, new User(), "Pending", new Date()); // Adjust based on your test data

        // Mock the service method
        when(orderService.getOrderById(1L)).thenReturn(Optional.of(mockOrder));

        // Call the controller method
        ResponseEntity<Order> responseEntity = orderController.getOrderById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockOrder, responseEntity.getBody());
    }

    @Test
    void testGetOrderByIdNotFound() {
        // Mock the service method returning an empty Optional
        when(orderService.getOrderById(1L)).thenReturn(Optional.empty());

        // Call the controller method
        ResponseEntity<Order> responseEntity = orderController.getOrderById(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testGetAllOrdersForUser() {
        // Mock orders
        List<Order> mockOrders = new ArrayList<>(); // Adjust based on your test data

        // Mock the service method
        when(orderService.getAllOrdersForUser(1L)).thenReturn(mockOrders);

        // Call the controller method
        ResponseEntity<List<Order>> responseEntity = orderController.getAllOrdersForUser(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockOrders, responseEntity.getBody());
    }

    @Test
    void testUpdateOrderStatus() {
        // Mock updated order
        Order mockUpdatedOrder = new Order(1L, new User(), "Shipped", new Date()); // Adjust based on your test data

        // Mock the service method
        when(orderService.updateOrderStatus(1L, "Shipped")).thenReturn(Optional.of(mockUpdatedOrder));

        // Call the controller method
        ResponseEntity<Order> responseEntity = orderController.updateOrderStatus(1L, "Shipped");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockUpdatedOrder, responseEntity.getBody());
    }

    @Test
    void testUpdateOrderStatusNotFound() {
        // Mock the service method returning an empty Optional
        when(orderService.updateOrderStatus(1L, "Shipped")).thenReturn(Optional.empty());

        // Call the controller method
        ResponseEntity<Order> responseEntity = orderController.updateOrderStatus(1L, "Shipped");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testDeleteOrder() {
        // Mock the service method returning true (indicating successful deletion)
        when(orderService.deleteOrder(1L)).thenReturn(true);

        // Call the controller method
        ResponseEntity<Void> responseEntity = orderController.deleteOrder(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteOrderNotFound() {
        // Mock the service method returning false (indicating order not found)
        when(orderService.deleteOrder(1L)).thenReturn(false);

        // Call the controller method
        ResponseEntity<Void> responseEntity = orderController.deleteOrder(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}

