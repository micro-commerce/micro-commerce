package org.micro.commerce.product.infrastructure.transformer;

import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.micro.commerce.product.domain.aggregate.ProductAggregate;
import org.micro.commerce.product.domain.converter.ProductEventConverter;
import org.micro.commerce.product.domain.event.ProductCreationFailed;
import org.micro.commerce.product.domain.event.ProductCreationRequested;
import org.micro.commerce.product.domain.event.ProductCreationValidated;
import org.micro.commerce.product.domain.event.ProductEvent;
import org.micro.commerce.product.domain.exception.ValidationException;
import org.micro.commerce.product.infrastructure.configuration.StateStoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductCreationRequestTransformer implements ValueTransformer<ProductEvent, ProductEvent> {

    private ProductEventConverter<ProductCreationRequested> productCreationRequestedConverter;
    private ProductEventConverter<ProductCreationFailed> productCreationFailedConverter;
    private ProductEventConverter<ProductCreationValidated> productCreationValidatedConverter;

    private ProcessorContext context;
    private KeyValueStore<String, ProductAggregate> productAggregateStateStoreSupplier;

    public ProductCreationRequestTransformer(
            ProductEventConverter<ProductCreationRequested> productCreationRequestedConverter,
            ProductEventConverter<ProductCreationFailed> productCreationFailedConverter,
            ProductEventConverter<ProductCreationValidated> productCreationValidatedConverter
    ) {
        this.productCreationRequestedConverter = productCreationRequestedConverter;
        this.productCreationFailedConverter = productCreationFailedConverter;
        this.productCreationValidatedConverter = productCreationValidatedConverter;
    }

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        this.productAggregateStateStoreSupplier = (KeyValueStore<String, ProductAggregate>) context.getStateStore(StateStoreProperties.PRODUCT_AGGREGATE_STATE_STORE_NAME);
    }

    @Override
    public ProductEvent transform(ProductEvent event) {
        ProductAggregate productAggregate = new ProductAggregate();
        try{
            productAggregate.apply(productCreationRequestedConverter.toSame(event));
            return productCreationValidatedConverter.toNew(event);
        } catch (ValidationException validationException){
            return productCreationFailedConverter.toNew(event);
        } finally {
            productAggregateStateStoreSupplier.put(event.getModel().getId().toString(), productAggregate);
        }

    }

    @Override
    public ProductEvent punctuate(long timestamp) {
        return null;
    }

    @Override
    public void close() {

    }

}
