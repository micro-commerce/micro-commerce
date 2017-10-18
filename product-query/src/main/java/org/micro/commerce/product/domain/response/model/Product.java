package org.micro.commerce.product.domain.response.model;

import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.util.UUID;

public class Product extends ResourceSupport {

    private UUID productId;

    private String name;

    private String description;

    private BigDecimal price;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
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
