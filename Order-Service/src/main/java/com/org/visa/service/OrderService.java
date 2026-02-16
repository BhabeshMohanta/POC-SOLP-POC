package com.org.visa.service;

import com.org.visa.entity.Order;
import com.org.visa.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    public String placeOrder(Long productId, int quantity, double amount) {
        // Call Product Service via Eureka (API Gateway or direct service discovery)
        String url = "http://PRODUCT-SERVICE/products/" + productId;
        Product product = restTemplate.getForObject(url, Product.class);

        if (product != null && product.getStock() >= quantity && product.getPrice() == amount) {
            Order order = new Order(productId, quantity, amount, "SUCCESS");
            orderRepository.save(order);
            return "Order placed successfully!";
        } else {
            Order order = new Order(productId, quantity, amount, "FAILED");
            orderRepository.save(order);
            return "Order failed: Product unavailable or price mismatch.";
        }
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Inner DTO class for Product response
    static class Product {
        private Long id;
        private String name;
        private double price;
        private int stock;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }

        public int getStock() { return stock; }
        public void setStock(int stock) { this.stock = stock; }
    }
}

