package org.micro.commerce.product.domain.event;

import org.micro.commerce.product.domain.model.Product;

public class ProductEvent extends Event<Product> {

    public ProductEvent() {
    }

    public ProductEvent(EventType eventType) {
        this.setEventType(eventType);
    }

}
