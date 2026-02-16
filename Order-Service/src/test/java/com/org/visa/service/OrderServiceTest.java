package com.org.visa.service;

import com.org.visa.entity.Order;
import com.org.visa.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testPlaceOrderSuccess() {
        // Mock product response
        OrderService.Product product = new OrderService.Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(1000.0);
        product.setStock(10);

        when(restTemplate.getForObject(anyString(), eq(OrderService.Product.class)))
                .thenReturn(product);

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        String result = orderService.placeOrder(1L, 2, 1000.0);

        assertEquals("Order placed successfully!", result);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testPlaceOrderFailure() {
        // Mock product with insufficient stock
        OrderService.Product product = new OrderService.Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(1000.0);
        product.setStock(1);

        when(restTemplate.getForObject(anyString(), eq(OrderService.Product.class)))
                .thenReturn(product);

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        String result = orderService.placeOrder(1L, 5, 1000.0);

        assertEquals("Order failed: Product unavailable or price mismatch.", result);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testGetAllOrders() {
        Order order1 = new Order(1L, 2, 1000.0, "SUCCESS");
        Order order2 = new Order(2L, 1, 500.0, "FAILED");

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.getAllOrders();

        assertEquals(2, orders.size());
        assertEquals("SUCCESS", orders.get(0).getStatus());
        assertEquals("FAILED", orders.get(1).getStatus());
    }
}
