package org.micro.commerce.product.domain.event;

public class ProductCreationFailed extends ProductEvent {

    public ProductCreationFailed() {
        super(EventType.PRODUCT_CREATION_FAILED);
    }
}
