package com.example.JavaEcommerce.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JavaEcommerce.Controller.OrderController;
import com.example.JavaEcommerce.Entity.Order;
import com.example.JavaEcommerce.Entity.User;
import com.example.JavaEcommerce.Repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;  // Assuming there is a UserService for user-related operations

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    // Create a new order
    public Order createOrder(Long userId, String status) {
        logger.info("Creating order for userId: {}, status: {}", userId, status);

        // Perform validation or business logic if needed
        Optional<User> optionalUser = userService.getUserById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Order order = new Order();
            order.setUser(user);
            order.setStatus(status);
            order.setOrderDate(new Date()); // You can set the order date based on your requirements

            logger.info("Order created successfully");
            return orderRepository.save(order);
        } else {
            logger.error("User not found for userId: {}", userId);
            throw new IllegalArgumentException("User not found");
        }
    }


    // Get details of a specific order by ID
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    // Get all orders for a user
    public List<Order> getAllOrdersForUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Update the status of an order
    public Optional<Order> updateOrderStatus(Long orderId, String status) {
        return orderRepository.findById(orderId).map(order -> {
            order.setStatus(status);
            return orderRepository.save(order);
        });
    }

    // Delete an order
    public boolean deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }
}
