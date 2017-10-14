package org.micro.commerce.product.domain.event;

import java.util.UUID;

public abstract class Event<T> {

    private UUID eventId;
    private UUID traceId;
    private T model;
    private EventType eventType;

    public Event() {
        eventId = UUID.randomUUID();
    }

    public UUID getEventId() {
        return eventId;
    }

    public UUID getTraceId() {
        return traceId;
    }

    public void setTraceId(UUID traceId) {
        this.traceId = traceId;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
