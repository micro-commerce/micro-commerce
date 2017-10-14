package org.micro.commerce.product.domain.event;

public class ProductCreated extends ProductEvent {

    public ProductCreated() {
        super(EventType.PRODUCT_CREATED);
    }

}
