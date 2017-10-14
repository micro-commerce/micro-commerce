package org.micro.commerce.product.domain.command;

import org.micro.commerce.product.domain.request.ProductCreationRequest;
import org.micro.commerce.product.domain.event.ProductCreationRequested;
import org.micro.commerce.product.domain.model.Product;
import org.micro.commerce.product.infrastructure.adapter.publisher.ProductEventsPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductCreateCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCreateCommand.class);

    ProductEventsPublisher productEventsPublisher;

    @Autowired
    public ProductCreateCommand(ProductEventsPublisher productEventsPublisher) {
        this.productEventsPublisher = productEventsPublisher;
    }

    public void requestProductCreation(ProductCreationRequest request){
        Product product = new Product();
        product.setId(request.getId());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        ProductCreationRequested event = new ProductCreationRequested(UUID.randomUUID(), product);
        productEventsPublisher.send(
                event.getModel().getId().toString(),
                event
        );
    }

//    public void when(ProductCreationRequested productEvent){
//        ProductAggregate productAggregate = productEvent.getModel();
//
//        if(productAggregate.getPrice().compareTo(BigDecimal.ZERO) == 0 || productAggregate.getPrice().compareTo(BigDecimal.ZERO) == -1){
//            ProductCreationValidationFailed event = new ProductCreationValidationFailed();
//            event.setModel(productAggregate);
//            event.setTraceId(productEvent.getTraceId());
//            productEventsPublisher.send(
//                    event
//            );
//            return;
//        }
//
//        ProductCreationValidated event = new ProductCreationValidated();
//        event.setModel(productAggregate);
//        event.setTraceId(productEvent.getTraceId());
//        productEventsPublisher.send(
//                event
//        );
//    }
//
//    public void when(ProductCreationValidated productEvent){
//        ProductCreated event = new ProductCreated();
//        event.setModel(productEvent.getModel());
//        event.setTraceId(productEvent.getTraceId());
//        productEventsPublisher.send(
//                event
//        );
//    }
//
//    public void when(ProductCreationValidationFailed productEvent) {
//        ProductCreationFailed event = new ProductCreationFailed();
//        event.setModel(productEvent.getModel());
//        event.setTraceId(productEvent.getTraceId());
//        productEventsPublisher.send(
//                event
//        );
//    }

//    public void when(ProductCreated productEvent) {
//
//    }
//
//    public void when(ProductCreationFailed productEvent) {
//
//    }
}
