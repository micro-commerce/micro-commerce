package org.micro.commerce.product.infrastructure.processor;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.micro.commerce.product.domain.aggregate.ProductAggregate;
import org.micro.commerce.product.domain.converter.ProductEventConverter;
import org.micro.commerce.product.domain.event.ProductCreationFailed;
import org.micro.commerce.product.domain.event.ProductEvent;
import org.micro.commerce.product.infrastructure.configuration.StateStoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductCreationFailedProcessor implements Processor<String, ProductEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCreationFailedProcessor.class);

    private ProductEventConverter<ProductCreationFailed> productCreationFailedConverter;

    private ProcessorContext context;
    private KeyValueStore<String, ProductAggregate> productAggregateStateStoreSupplier;

    public ProductCreationFailedProcessor(ProductEventConverter<ProductCreationFailed> productCreationFailedConverter) {
        this.productCreationFailedConverter = productCreationFailedConverter;
    }

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        this.productAggregateStateStoreSupplier = (KeyValueStore<String, ProductAggregate>) context.getStateStore(StateStoreProperties.PRODUCT_AGGREGATE_STATE_STORE_NAME);
    }

    @Override
    public void process(String key, ProductEvent event) {
        LOGGER.info("ProductCreationFailedProcessor eventId: " + event.getModel().getId());
        ProductAggregate productAggregate = productAggregateStateStoreSupplier.get(key);
        ProductCreationFailed productCreationFailed = productCreationFailedConverter.toSame(event);
        LOGGER.info("ProductCreationFailedProcessor converted eventId: " + event.getModel().getId());
        productAggregate.apply(productCreationFailed);
        productAggregateStateStoreSupplier.put(key, productAggregate);
    }

    @Override
    public void punctuate(long timestamp) {

    }

    @Override
    public void close() {

    }

}
