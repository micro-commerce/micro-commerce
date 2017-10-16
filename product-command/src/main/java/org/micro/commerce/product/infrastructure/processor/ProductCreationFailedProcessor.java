package org.micro.commerce.product.infrastructure.processor;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.micro.commerce.product.domain.aggregate.ProductAggregate;
import org.micro.commerce.product.domain.converter.ProductEventConverter;
import org.micro.commerce.product.domain.event.ProductCreationFailed;
import org.micro.commerce.product.domain.event.ProductEvent;
import org.micro.commerce.product.domain.event.ProductRevisionMismatched;
import org.micro.commerce.product.domain.exception.EventRevisionMismatch;
import org.micro.commerce.product.infrastructure.adapter.publisher.ProductEventPublisher;
import org.micro.commerce.product.infrastructure.configuration.StateStoreProperties;

public class ProductCreationFailedProcessor implements Processor<String, ProductEvent> {

    private ProductEventConverter<ProductCreationFailed> productCreationFailedConverter;
    private ProductEventPublisher productEventPublisher;

    private ProcessorContext context;
    private KeyValueStore<String, ProductAggregate> productAggregateStateStoreSupplier;

    public ProductCreationFailedProcessor(
            ProductEventConverter<ProductCreationFailed> productCreationFailedConverter,
            ProductEventPublisher productEventPublisher
    ) {
        this.productCreationFailedConverter = productCreationFailedConverter;
        this.productEventPublisher = productEventPublisher;
    }

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        this.productAggregateStateStoreSupplier = (KeyValueStore<String, ProductAggregate>) context.getStateStore(StateStoreProperties.PRODUCT_AGGREGATE_STATE_STORE_NAME);
    }

    @Override
    public void process(String key, ProductEvent event) {
        ProductAggregate productAggregate = productAggregateStateStoreSupplier.get(key);
        try {
            productAggregate.apply(productCreationFailedConverter.toSame(event));
        } catch (EventRevisionMismatch eventRevisionMismatch){
            productEventPublisher.send(key, new ProductRevisionMismatched(event.getTraceId(), event.getModel(), eventRevisionMismatch));
            return;
        }
        productAggregateStateStoreSupplier.put(key, productAggregate);
    }

    @Override
    public void punctuate(long timestamp) {

    }

    @Override
    public void close() {

    }

}
