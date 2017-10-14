package org.micro.commerce.product.domain.event;

public class ProductCreationValidated extends ProductEvent {

    public ProductCreationValidated() {
        super(EventType.PRODUCT_CREATION_VALIDATED);
    }
}
