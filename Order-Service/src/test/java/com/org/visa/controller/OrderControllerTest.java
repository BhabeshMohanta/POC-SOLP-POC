package com.org.visa.controller;

import com.org.visa.entity.Order;
import com.org.visa.repository.OrderRepository;
import com.org.visa.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    void testPlaceOrder() throws Exception {
        Mockito.when(orderService.placeOrder(1L, 2, 100.0))
                .thenReturn("Order placed successfully!");

        mockMvc.perform(post("/orders/place")
                        .param("productId", "1")
                        .param("quantity", "2")
                        .param("amount", "100.0"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order placed successfully!"));
    }

    @Test
    void testGetAllOrders() throws Exception {
        List<Order> orders = Arrays.asList(
                new Order(1L, 2, 100.0, "SUCCESS"),
                new Order(2L, 1, 50.0, "SUCCESS")
        );
        Mockito.when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("SUCCESS"))
                .andExpect(jsonPath("$[1].amount").value(50.0));
    }

    @Test
    void testAddOrder() throws Exception {
        Order order = new Order(1L, 2, 100.0, "SUCCESS");
        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":1,\"quantity\":2,\"amount\":100.0,\"status\":\"SUCCESS\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.amount").value(100.0));
    }
}

