package org.micro.commerce.product.infrastructure.adapter.subscriber;

import org.micro.commerce.product.domain.event.ProductCreated;
import org.micro.commerce.product.infrastructure.adapter.writer.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventsSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventsSubscriber.class);

    private Writer writer;

    public ProductEventsSubscriber(Writer writer) {
        this.writer = writer;
    }

    @KafkaListener(topics = "${kafka.topic.product-created}")
    public void receive(ProductCreated event) {
        LOGGER.info("isProductCreated: " + (event instanceof ProductCreated) + " received payload='{}'", event.getModel());
        writer.persist(event);
    }

}
