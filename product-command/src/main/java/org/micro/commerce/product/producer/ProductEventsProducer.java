package org.micro.commerce.product.producer;

import org.micro.commerce.product.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductEventsProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventsProducer.class);

    @Value("${kafka.topic.product-creation-requested}")
    private String productCreationRequestedTopic;

    @Value("${kafka.topic.product-creation-validated}")
    private String productCreationValidatedTopic;

    @Value("${kafka.topic.product-creation-validation-failed}")
    private String productCreationValidationFailedTopic;

    @Value("${kafka.topic.product-created}")
    private String productCreatedTopic;

    @Value("${kafka.topic.product-creation-failed}")
    private String productCreationFailedTopic;

    private KafkaTemplate<String, Event> kafkaTemplate;

    @Autowired
    public ProductEventsProducer(KafkaTemplate<String, Event> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(ProductCreationRequested event) {
        LOGGER.info("sending payload='{}' to topic='{}'", event, productCreationRequestedTopic);
        kafkaTemplate.send(productCreationRequestedTopic, event);
    }

    public void send(ProductCreationValidated event) {
        LOGGER.info("sending payload='{}' to topic='{}'", event, productCreationValidatedTopic);
        kafkaTemplate.send(productCreationValidatedTopic, event);
    }

    public void send(ProductCreationValidationFailed event) {
        LOGGER.info("sending payload='{}' to topic='{}'", event, productCreationValidationFailedTopic);
        kafkaTemplate.send(productCreationValidationFailedTopic, event);
    }

    public void send(ProductCreated event) {
        LOGGER.info("sending payload='{}' to topic='{}'", event, productCreatedTopic);
        kafkaTemplate.send(productCreatedTopic, event);
    }

    public void send(ProductCreationFailed event) {
        LOGGER.info("sending payload='{}' to topic='{}'", event, productCreationFailedTopic);
        kafkaTemplate.send(productCreationFailedTopic, event);
    }

}
