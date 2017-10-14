package org.micro.commerce.product.infrastructure.adapter.subscriber;

import org.micro.commerce.product.domain.event.EventType;
import org.micro.commerce.product.domain.event.ProductEvent;
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

    @KafkaListener(topics = "${kafka.topic.product-event}")
    public void receive(ProductEvent event) {
        LOGGER.info("isProductCreated: " + EventType.PRODUCT_CREATED.equals(event.getEventType()) + " received payload='{}'", event.getModel());
        if(EventType.PRODUCT_CREATED.equals(event.getEventType())){
            writer.persist(event);
        }
    }

}
