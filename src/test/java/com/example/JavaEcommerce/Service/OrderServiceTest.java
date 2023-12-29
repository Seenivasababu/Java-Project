package com.example.JavaEcommerce.Service;

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

import com.example.JavaEcommerce.Entity.Order;
import com.example.JavaEcommerce.Entity.User;
import com.example.JavaEcommerce.Repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testCreateOrder() {
        // Mock user
        User mockUser = new User(1L, "TestUser", "test@example.com"); // Adjust based on your test data

        // Mock the service methods
        when(userService.getUserById(1L)).thenReturn(Optional.of(mockUser));

        // Mock the repository save method
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the service method
        Order result = orderService.createOrder(1L, "Pending");

        assertNotNull(result);
        assertEquals("Pending", result.getStatus());
        assertNotNull(result.getOrderDate());
        assertEquals(mockUser, result.getUser());
    }

    @Test
    void testGetOrderById() {
        // Mock the repository method
        when(orderRepository.findById(1L)).thenReturn(Optional.of(new Order()));

        // Call the service method
        Optional<Order> order = orderService.getOrderById(1L);

        assertTrue(order.isPresent());
        // Add more assertions as needed
    }

    @Test
    void testGetAllOrdersForUser() {
        // Mock the repository method
        when(orderRepository.findByUserId(1L)).thenReturn(new ArrayList<>());

        // Call the service method
        List<Order> orders = orderService.getAllOrdersForUser(1L);

        assertNotNull(orders);
        // Add more assertions as needed
    }

    @Test
    void testUpdateOrderStatus() {
        // Mock the repository and service methods
        Order mockOrder = new Order(1L, new User(), "Pending", new Date()); // Adjust based on your test data
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the service method
        Optional<Order> result = orderService.updateOrderStatus(1L, "Shipped");

        assertTrue(result.isPresent());
        assertEquals("Shipped", result.get().getStatus());
    }

    @Test
    void testDeleteOrder() {
        // Mock the repository method
        when(orderRepository.existsById(1L)).thenReturn(true);

        // Call the service method
        boolean result = orderService.deleteOrder(1L);

        assertTrue(result);
        verify(orderRepository, times(1)).deleteById(1L);
    }
}
