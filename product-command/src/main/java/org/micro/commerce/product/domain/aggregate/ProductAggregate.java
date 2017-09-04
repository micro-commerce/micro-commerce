package org.micro.commerce.product.domain.aggregate;

import org.micro.commerce.product.domain.event.ProductCreationRequested;
import org.micro.commerce.product.domain.exception.ValidationException;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductAggregate {

    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    public ProductAggregate() {
    }

    public void apply(ProductCreationRequested event) throws ValidationException {
        ProductAggregate productAggregate = event.getModel();
        if(productAggregate.getPrice().compareTo(BigDecimal.ZERO) == 0 || productAggregate.getPrice().compareTo(BigDecimal.ZERO) == -1){
            throw new ValidationException("price not valid");
        }
    }

    /**
     * TODO Apply methods will be added once the aggregate is persisted
     */
//    public void apply(ProductCreationRequested event){
//
//    }
//
//    public void apply(ProductCreationValidated event){
//
//    }
//
//    public void apply(ProductCreationValidationFailed event){
//
//    }
//
//    public void apply(ProductCreationFailed event){
//
//    }
//
//    public void apply(ProductCreated event){
//
//    }

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
