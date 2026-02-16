package com.org.visa.entity;

//import jakarta.persistence.*;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private int quantity;
    private double amount;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Order() { }

    public Order(Long id, Long productId, int quantity, double amount, String status) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
        this.status = status;
    }

    public Order(Long productId, int quantity, double amount, String status) {
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}
