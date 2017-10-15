package org.micro.commerce.product.infrastructure.transformer;

import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.micro.commerce.product.domain.aggregate.ProductAggregate;
import org.micro.commerce.product.domain.converter.ProductEventConverter;
import org.micro.commerce.product.domain.event.ProductCreated;
import org.micro.commerce.product.domain.event.ProductCreationValidated;
import org.micro.commerce.product.domain.event.ProductEvent;
import org.micro.commerce.product.domain.event.ProductVersionMismatched;
import org.micro.commerce.product.domain.exception.VersionMismatch;
import org.micro.commerce.product.infrastructure.configuration.StateStoreProperties;

public class ProductCreationValidatedTransformer implements ValueTransformer<ProductEvent, ProductEvent> {

    private ProductEventConverter<ProductCreationValidated> productCreationValidatedConverter;
    private ProductEventConverter<ProductCreated> productCreatedConverter;

    private ProcessorContext context;
    private KeyValueStore<String, ProductAggregate> productAggregateStateStoreSupplier;

    public ProductCreationValidatedTransformer(
            ProductEventConverter<ProductCreationValidated> productCreationValidatedConverter,
            ProductEventConverter<ProductCreated> productCreatedConverter
    ) {
        this.productCreationValidatedConverter = productCreationValidatedConverter;
        this.productCreatedConverter = productCreatedConverter;
    }

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        this.productAggregateStateStoreSupplier = (KeyValueStore<String, ProductAggregate>) context.getStateStore(StateStoreProperties.PRODUCT_AGGREGATE_STATE_STORE_NAME);
    }

    @Override
    public ProductEvent transform(ProductEvent event) {
        ProductAggregate productAggregate = productAggregateStateStoreSupplier.get(event.getModel().getId().toString());
        try {
            productAggregate.apply(productCreationValidatedConverter.toSame(event));
        } catch (VersionMismatch versionMismatch){
            return new ProductVersionMismatched(event.getTraceId(), event.getModel(), versionMismatch);
        }
        productAggregateStateStoreSupplier.put(event.getModel().getId().toString(), productAggregate);
        return productCreatedConverter.toNew(event);
    }

    @Override
    public ProductEvent punctuate(long timestamp) {
        return null;
    }

    @Override
    public void close() {

    }
}
