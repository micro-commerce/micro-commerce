package org.micro.commerce.product.domain.event;

import org.micro.commerce.product.domain.model.Product;

import java.util.UUID;

public class ProductCreationRequested extends ProductEvent {

    public ProductCreationRequested(){
        super(EventType.PRODUCT_CREATION_REQUESTED);
    }

    public ProductCreationRequested(UUID traceId, Product model) {
        super(EventType.PRODUCT_CREATION_REQUESTED);
        this.setEventId(UUID.randomUUID());
        this.setTraceId(traceId);
        this.setModel(model);
    }
}
