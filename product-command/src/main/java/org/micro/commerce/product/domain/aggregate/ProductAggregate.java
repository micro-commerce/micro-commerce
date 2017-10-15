package org.micro.commerce.product.domain.aggregate;

import org.micro.commerce.product.domain.event.*;
import org.micro.commerce.product.domain.exception.ValidationException;
import org.micro.commerce.product.domain.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductAggregate extends BaseAggregate<ProductEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductAggregate.class);

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;

    public void apply(ProductCreationRequested event){
        LOGGER.warn("Event Model Price: " + event.getModel().getPrice());
        super.apply(event);
        init(event.getModel());
        LOGGER.warn("Model Price: " + getPrice());
        if(getPrice().compareTo(BigDecimal.ZERO) == 0 || getPrice().compareTo(BigDecimal.ZERO) == -1){
            LOGGER.warn("Price Not Valid!");
            throw new ValidationException("price not valid");
        }
    }

    public void apply(ProductCreationValidated event){
        super.apply(event);
    }

    public void apply(ProductCreationFailed event){
        super.apply(event);
    }

    public void apply(ProductCreated event){
        super.apply(event);
    }

    private void init(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
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
