package org.micro.commerce.product.infrastructure.adapter.publisher;

import org.micro.commerce.product.domain.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductEventsPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventsPublisher.class);

    @Value("${kafka.topic.product-event}")
    private String productEventTopic;

//    @Value("${kafka.topic.product-creation-requested}")
//    private String productCreationRequestedTopic;

//    @Value("${kafka.topic.product-creation-validated}")
//    private String productCreationValidatedTopic;
//
//    @Value("${kafka.topic.product-creation-validation-failed}")
//    private String productCreationValidationFailedTopic;
//
//    @Value("${kafka.topic.product-created}")
//    private String productCreatedTopic;
//
//    @Value("${kafka.topic.product-creation-failed}")
//    private String productCreationFailedTopic;

    private KafkaTemplate<String, Event> kafkaTemplate;

    @Autowired
    public ProductEventsPublisher(KafkaTemplate<String, Event> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String key, ProductEvent event) {
        LOGGER.info("sending payload='{}' to topic='{}'", event, productEventTopic);
        kafkaTemplate.send(productEventTopic, key, event);
    }

//    public void send(ProductCreationValidated event) {
//        LOGGER.info("sending payload='{}' to topic='{}'", event, productCreationValidatedTopic);
//        kafkaTemplate.send(productCreationValidatedTopic, event);
//    }
//
//    public void send(ProductCreationValidationFailed event) {
//        LOGGER.info("sending payload='{}' to topic='{}'", event, productCreationValidationFailedTopic);
//        kafkaTemplate.send(productCreationValidationFailedTopic, event);
//    }
//
//    public void send(ProductCreated event) {
//        LOGGER.info("sending payload='{}' to topic='{}'", event, productCreatedTopic);
//        kafkaTemplate.send(productCreatedTopic, event);
//    }
//
//    public void send(ProductCreationFailed event) {
//        LOGGER.info("sending payload='{}' to topic='{}'", event, productCreationFailedTopic);
//        kafkaTemplate.send(productCreationFailedTopic, event);
//    }

}
