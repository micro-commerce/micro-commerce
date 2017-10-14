package org.micro.commerce.product.domain.event;

import org.micro.commerce.product.domain.model.Product;

public class ProductEvent extends Event<Product> {

    public ProductEvent() {
    }

//    public ProductEvent(ProductEvent productEvent) {
//        this.setEventId(productEvent.getEventId());
//        this.setEventType(productEvent.getEventType());
//        this.setTraceId(productEvent.getTraceId());
//        this.setModel(productEvent.getModel());
//    }
//
//    public ProductEvent(ProductEvent productEvent, EventType eventType) {
//        this.setEventId(UUID.randomUUID());
//        this.setEventType(eventType);
//        this.setTraceId(productEvent.getTraceId());
//        this.setModel(productEvent.getModel());
//    }

    public ProductEvent(EventType eventType) {
        this.setEventType(eventType);
    }

}
