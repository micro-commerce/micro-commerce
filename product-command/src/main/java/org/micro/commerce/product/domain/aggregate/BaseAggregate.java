package org.micro.commerce.product.domain.aggregate;


import org.micro.commerce.product.domain.event.Event;
import org.micro.commerce.product.domain.exception.EventRevisionMismatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class BaseAggregate<T extends Event> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseAggregate.class);

    private List<T> events = new ArrayList<>();

    public void apply(T event){
        LOGGER.info("event type: " + event.getEventType());
        LOGGER.info("event id: " + event.getEventId());
        Long latestRevision = latestRevision();
        if (event.getRevision() <= latestRevision){
            throw new EventRevisionMismatch(String.format("Event Revision Not Consistent -> event type: %s, current event revision: %d, latest event revision: %d", event.getEventType(), event.getRevision(), latestRevision));
        }
        events.add(event);
        events.stream().forEach(x -> LOGGER.info(String.format("Aggregate EventList eventType: %s, version: %s", x.getEventType(), x.getRevision())));
    }

    public List<T> getEvents() {
        return events;
    }

    public void setEvents(List<T> events) {
        this.events = events;
    }

    public Long latestRevision(){
        return events
                .stream()
                .map(event -> event.getRevision())
                .max(Comparator.naturalOrder())
                .orElse(0L);
    }

}
