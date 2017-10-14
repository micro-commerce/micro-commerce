package org.micro.commerce.product.infrastructure.processor;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.micro.commerce.product.domain.aggregate.ProductAggregate;
import org.micro.commerce.product.domain.converter.ProductEventConverter;
import org.micro.commerce.product.domain.event.ProductCreated;
import org.micro.commerce.product.domain.event.ProductEvent;
import org.micro.commerce.product.infrastructure.configuration.StateStoreProperties;

public class ProductCreatedProcessor implements Processor<String, ProductEvent> {

    private ProductEventConverter<ProductCreated> productCreatedConverter;

    private ProcessorContext context;
    private KeyValueStore<String, ProductAggregate> productAggregateStateStoreSupplier;

    public ProductCreatedProcessor(ProductEventConverter<ProductCreated> productCreatedConverter) {
        this.productCreatedConverter = productCreatedConverter;
    }

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        this.productAggregateStateStoreSupplier = (KeyValueStore<String, ProductAggregate>) context.getStateStore(StateStoreProperties.PRODUCT_AGGREGATE_STATE_STORE_NAME);
    }

    @Override
    public void process(String key, ProductEvent event) {
        ProductAggregate productAggregate = productAggregateStateStoreSupplier.get(key);
        productAggregate.apply(productCreatedConverter.toSame(event));
        productAggregateStateStoreSupplier.put(key, productAggregate);
    }

    @Override
    public void punctuate(long timestamp) {

    }

    @Override
    public void close() {

    }

}
