package org.micro.commerce.product.consumer;

import org.micro.commerce.product.command.ProductCreateCommand;
import org.micro.commerce.product.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventsConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventsConsumer.class);

    private ProductCreateCommand productCreateCommand;

    @Autowired
    public ProductEventsConsumer(ProductCreateCommand productCreateCommand) {
        this.productCreateCommand = productCreateCommand;
    }

    @KafkaListener(topics = "${kafka.topic.product-creation-requested}")
    public void receive(ProductCreationRequested event) {
        LOGGER.info("isProductCreationRequested: " + (event instanceof ProductCreationRequested) + " received payload='{}'", event.getModel());
        productCreateCommand.when(event);
    }

    @KafkaListener(topics = "${kafka.topic.product-creation-validated}")
    public void receive(ProductCreationValidated event) {
        LOGGER.info("isProductCreationValidated: " + (event instanceof ProductCreationValidated) + " received payload='{}'", event.getModel());
        productCreateCommand.when(event);
    }

    @KafkaListener(topics = "${kafka.topic.product-creation-validation-failed}")
    public void receive(ProductCreationValidationFailed event) {
        LOGGER.info("isProductCreationValidationFailed: " + (event instanceof ProductCreationValidationFailed) + " received payload='{}'", event.getModel());
        productCreateCommand.when(event);
    }

    @KafkaListener(topics = "${kafka.topic.product-created}")
    public void receive(ProductCreated event) {
        LOGGER.info("isProductCreated: " + (event instanceof ProductCreated) + " received payload='{}'", event.getModel());
        productCreateCommand.when(event);
    }

    @KafkaListener(topics = "${kafka.topic.product-creation-failed}")
    public void receive(ProductCreationFailed event) {
        LOGGER.info("isProductCreationFailed: " + (event instanceof ProductCreationFailed) + " received payload='{}'", event.getModel());
        productCreateCommand.when(event);
    }

}
