package com.org.visa.controller;

import com.org.visa.entity.Product;
import com.org.visa.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void testGetAllProducts() throws Exception {
        Mockito.when(productRepository.findAll())
                .thenReturn(Arrays.asList(
                        new Product(1L, "Laptop", 1000.0, 10),
                        new Product(2L, "Phone", 500.0, 20)
                ));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[1].name").value("Phone"));
    }

    @Test
    void testGetProductById() throws Exception {
        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(new Product(1L, "Laptop", 1000.0, 10)));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void testAddProduct() throws Exception {
        Product product = new Product(1L, "Tablet", 300.0, 15);
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Tablet\",\"price\":300.0,\"stock\":15}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tablet"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product existing = new Product(1L, "Laptop", 1000.0, 10);
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(productRepository.save(any(Product.class)))
                .thenReturn(new Product(1L, "Laptop Pro", 1200.0, 8));

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Laptop Pro\",\"price\":1200.0,\"stock\":8}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop Pro"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Mockito.doNothing().when(productRepository).deleteById(eq(1L));

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully!"));
    }
}
