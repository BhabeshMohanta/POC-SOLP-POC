package com.org.visa.controller;

import com.org.visa.entity.Order;
import com.org.visa.repository.OrderRepository;
import com.org.visa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/place")
    public String placeOrder(@RequestParam Long productId,
                             @RequestParam int quantity, @RequestParam double amount) {
        return orderService.placeOrder(productId, quantity, amount);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public Order addOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }
}
