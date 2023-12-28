package com.example.JavaEcommerce.Controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.JavaEcommerce.Entity.Order;
import com.example.JavaEcommerce.Service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    // API to create a new order
    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestParam Long userId,
            @RequestParam String status) {
    	logger.info(status,userId);
        try {
            Order createdOrder = orderService.createOrder(userId, status);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (Exception e) {
        	logger.info(e.getMessage());
            // Handle the exception, e.g., return a proper error response
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // API to get details of a specific order
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Optional<Order> order = orderService.getOrderById(orderId);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // API to get all orders for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getAllOrdersForUser(@PathVariable Long userId) {
        List<Order> userOrders = orderService.getAllOrdersForUser(userId);
        return new ResponseEntity<>(userOrders, HttpStatus.OK);
    }

    // API to update the status of an order
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        Optional<Order> updatedOrder = orderService.updateOrderStatus(orderId, status);
        return updatedOrder.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // API to delete an order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        boolean deleted = orderService.deleteOrder(orderId);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

