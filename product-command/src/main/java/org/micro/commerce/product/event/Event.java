package org.micro.commerce.product.event;

import java.util.UUID;

public abstract class Event<T> {

    private UUID eventId;
    private UUID traceId;
    private T model;

    public Event() {
        eventId = UUID.randomUUID();
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
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
}
