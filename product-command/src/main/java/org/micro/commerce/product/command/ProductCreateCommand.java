package org.micro.commerce.product.command;

import org.micro.commerce.product.aggregate.ProductAggregate;
import org.micro.commerce.product.controller.request.ProductCreationRequest;
import org.micro.commerce.product.event.*;
import org.micro.commerce.product.producer.ProductEventsProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ProductCreateCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCreateCommand.class);

    ProductEventsProducer productEventsProducer;

    @Autowired
    public ProductCreateCommand(ProductEventsProducer productEventsProducer) {
        this.productEventsProducer = productEventsProducer;
    }

    public void requestProductCreation(ProductCreationRequest request){
        ProductAggregate productAggregate = new ProductAggregate();
        productAggregate.setId(request.getId());
        productAggregate.setName(request.getName());
        productAggregate.setDescription(request.getDescription());
        productAggregate.setPrice(request.getPrice());

        ProductCreationRequested event = new ProductCreationRequested();
        event.setModel(productAggregate);
        event.setTraceId(UUID.randomUUID());
        productEventsProducer.send(
                event
        );
    }

    public void when(ProductCreationRequested productEvent){
        ProductAggregate productAggregate = productEvent.getModel();

        if(productAggregate.getPrice().compareTo(BigDecimal.ZERO) == 0 || productAggregate.getPrice().compareTo(BigDecimal.ZERO) == -1){
            ProductCreationValidationFailed event = new ProductCreationValidationFailed();
            event.setModel(productAggregate);
            event.setTraceId(productEvent.getTraceId());
            productEventsProducer.send(
                    event
            );
            return;
        }

        ProductCreationValidated event = new ProductCreationValidated();
        event.setModel(productAggregate);
        event.setTraceId(productEvent.getTraceId());
        productEventsProducer.send(
                event
        );
    }

    public void when(ProductCreationValidated productEvent){
        ProductCreated event = new ProductCreated();
        event.setModel(productEvent.getModel());
        event.setTraceId(productEvent.getTraceId());
        productEventsProducer.send(
                event
        );
    }

    public void when(ProductCreationValidationFailed productEvent) {
        ProductCreationFailed event = new ProductCreationFailed();
        event.setModel(productEvent.getModel());
        event.setTraceId(productEvent.getTraceId());
        productEventsProducer.send(
                event
        );
    }

    public void when(ProductCreated productEvent) {

    }

    public void when(ProductCreationFailed productEvent) {

    }
}
