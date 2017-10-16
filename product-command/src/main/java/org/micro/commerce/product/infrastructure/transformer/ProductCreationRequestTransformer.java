package org.micro.commerce.product.infrastructure.transformer;

import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.micro.commerce.product.domain.aggregate.ProductAggregate;
import org.micro.commerce.product.domain.converter.ProductEventConverter;
import org.micro.commerce.product.domain.event.*;
import org.micro.commerce.product.domain.exception.ProductNotValid;
import org.micro.commerce.product.domain.exception.EventRevisionMismatch;
import org.micro.commerce.product.infrastructure.configuration.StateStoreProperties;

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
        ProductEvent resultEvent = null;
        ProductAggregate productAggregate = productAggregateStateStoreSupplier.get(event.getModel().getId().toString());
        if(productAggregate == null)
            productAggregate = new ProductAggregate();

        try {
            productAggregate.apply(productCreationRequestedConverter.toSame(event));
            resultEvent = productCreationValidatedConverter.toNew(event);
        } catch (ProductNotValid productNotValid){
            resultEvent = productCreationFailedConverter.toNew(event);
            resultEvent.setErrorInfo(new ErrorInfo(productNotValid));
        } catch (EventRevisionMismatch eventRevisionMismatch){
            resultEvent = new ProductRevisionMismatched(event.getTraceId(), event.getModel(), eventRevisionMismatch);
        } finally {
            if(!EventType.PRODUCT_REVISION_MISMATCHED.equals(resultEvent.getEventType())){
                productAggregateStateStoreSupplier.put(event.getModel().getId().toString(), productAggregate);
            }
        }
        return resultEvent;
    }

    @Override
    public ProductEvent punctuate(long timestamp) {
        return null;
    }

    @Override
    public void close() {

    }

}
