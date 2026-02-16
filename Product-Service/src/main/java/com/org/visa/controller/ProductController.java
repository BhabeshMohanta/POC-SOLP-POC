package com.org.visa.controller;


import com.org.visa.entity.Product;
import com.org.visa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {

        return productRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {

        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product existing = productRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(product.getName());
            existing.setPrice(product.getPrice());
            existing.setStock(product.getStock());
            return productRepository.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "Product deleted successfully!";
    }
}
