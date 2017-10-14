package org.micro.commerce.product.infrastructure.configuration;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.processor.StateStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.micro.commerce.product.domain.aggregate.ProductAggregate;
import org.micro.commerce.product.domain.converter.ProductEventConverter;
import org.micro.commerce.product.domain.event.*;
import org.micro.commerce.product.infrastructure.processor.ProductCreatedProcessor;
import org.micro.commerce.product.infrastructure.processor.ProductCreationFailedProcessor;
import org.micro.commerce.product.infrastructure.transformer.ProductCreationRequestTransformer;
import org.micro.commerce.product.infrastructure.transformer.ProductCreationValidatedTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class EventStreamConfiguration {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.topic.product-event}")
    private String productEventTopic;

    @Value("${kafka.topic.product-event-log}")
    private String productEventLogTopic;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public StreamsConfig productStreamsConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "product-event-stream");
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "product-event-stream-client");
        return new StreamsConfig(props);
    }

    @Bean
    public StateStoreSupplier productAggregateStateStoreSupplier(){
        return Stores.create(StateStoreProperties.PRODUCT_AGGREGATE_STATE_STORE_NAME)
                .withKeys(Serdes.String())
                .withValues(new JsonSerde<>(ProductAggregate.class))
                .persistent()
                .build();
    }

    @Bean
    public KStream<String, ProductEvent> productCreationRequestedStream(
            KStreamBuilder kStreamBuilder,
            StateStoreSupplier productAggregateStateStoreSupplier,
            ProductEventConverter<ProductCreationRequested> productCreationRequestedConverter,
            ProductEventConverter<ProductCreationFailed> productCreationFailedConverter,
            ProductEventConverter<ProductCreationValidated> productCreationValidatedConverter,
            ProductEventConverter<ProductCreated> productCreatedConverter
    ) {
        kStreamBuilder.addStateStore(productAggregateStateStoreSupplier);
        KStream<String, ProductEvent> stream = kStreamBuilder.stream(Serdes.String(), new JsonSerde<>(ProductEvent.class), productEventTopic);

        stream
                .filter((key, value) -> value.getEventType().equals(EventType.PRODUCT_CREATION_REQUESTED))
                .transformValues(() -> new ProductCreationRequestTransformer(productCreationRequestedConverter, productCreationFailedConverter, productCreationValidatedConverter), StateStoreProperties.PRODUCT_AGGREGATE_STATE_STORE_NAME)
                .to(Serdes.String(), new JsonSerde<>(ProductEvent.class), productEventTopic);

        stream
                .filter((key, value) -> value.getEventType().equals(EventType.PRODUCT_CREATION_VALIDATED))
                .transformValues(() -> new ProductCreationValidatedTransformer(productCreationValidatedConverter, productCreatedConverter), StateStoreProperties.PRODUCT_AGGREGATE_STATE_STORE_NAME)
                .to(Serdes.String(), new JsonSerde<>(ProductEvent.class), productEventTopic);

        stream
                .filter((key, value) -> value.getEventType().equals(EventType.PRODUCT_CREATED))
                .process(() -> new ProductCreatedProcessor(productCreatedConverter), StateStoreProperties.PRODUCT_AGGREGATE_STATE_STORE_NAME);

        stream
                .filter((key, value) -> value.getEventType().equals(EventType.PRODUCT_CREATION_FAILED))
                .process(() -> new ProductCreationFailedProcessor(productCreationFailedConverter), StateStoreProperties.PRODUCT_AGGREGATE_STATE_STORE_NAME);

        stream.to(Serdes.String(), new JsonSerde<>(ProductEvent.class), productEventLogTopic);

        stream.print();

        return stream;
    }

}