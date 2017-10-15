package org.micro.commerce.product.domain.command;

import org.micro.commerce.product.domain.request.ProductCreationRequest;
import org.micro.commerce.product.domain.event.ProductCreationRequested;
import org.micro.commerce.product.domain.model.Product;
import org.micro.commerce.product.infrastructure.adapter.publisher.ProductEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductCreateCommand {

    ProductEventPublisher productEventPublisher;

    @Autowired
    public ProductCreateCommand(ProductEventPublisher productEventPublisher) {
        this.productEventPublisher = productEventPublisher;
    }

    public void requestProductCreation(ProductCreationRequest request){
        ProductCreationRequested event = new ProductCreationRequested(UUID.randomUUID(), new Product(request));
        productEventPublisher.send(
                event.getModel().getId().toString(),
                event
        );
    }

}
