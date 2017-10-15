package org.micro.commerce.product.domain.event;

import org.micro.commerce.product.domain.model.Product;

import java.util.UUID;

public class ProductVersionMismatched extends ProductEvent {

    public ProductVersionMismatched() {
        super(EventType.PRODUCT_VERSION_MISMATCHED);
    }

    public ProductVersionMismatched(UUID traceId, Product model) {
        super(EventType.PRODUCT_VERSION_MISMATCHED);
        this.setEventId(UUID.randomUUID());
        this.setTraceId(traceId);
        this.setVersion(0L);
        this.setModel(model);
    }

}
