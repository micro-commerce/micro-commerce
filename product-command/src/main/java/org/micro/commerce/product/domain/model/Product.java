package org.micro.commerce.product.domain.model;

import org.micro.commerce.product.domain.request.ProductCreationRequest;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;

    public Product() {
    }

    public Product(UUID productId, ProductCreationRequest request) {
        this.id = productId;
        this.name = request.getName();
        this.description = request.getDescription();
        this.price = request.getPrice();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
