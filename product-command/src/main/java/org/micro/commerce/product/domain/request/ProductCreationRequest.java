package org.micro.commerce.product.domain.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class ProductCreationRequest {

    @NotNull
    private String name;

    private String description;

    @NotNull
    private BigDecimal price;

    public ProductCreationRequest() {
    }

    public ProductCreationRequest(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
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
