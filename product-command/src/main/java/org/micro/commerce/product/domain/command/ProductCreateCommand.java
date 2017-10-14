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

}
