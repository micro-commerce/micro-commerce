package org.micro.commerce.product.domain.event;

import org.micro.commerce.product.domain.model.Product;

import java.util.UUID;

public class ProductRevisionMismatched extends ProductEvent {

    public ProductRevisionMismatched() {
        super(EventType.PRODUCT_REVISION_MISMATCHED);
    }

    public ProductRevisionMismatched(UUID traceId, Product model, Exception exception) {
        super(EventType.PRODUCT_REVISION_MISMATCHED);
        this.setEventId(UUID.randomUUID());
        this.setTraceId(traceId);
        this.setRevision(0L);
        this.setErrorInfo(new ErrorInfo(exception));
        this.setModel(model);
    }

}
