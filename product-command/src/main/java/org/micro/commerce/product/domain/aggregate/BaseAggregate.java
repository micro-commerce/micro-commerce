package org.micro.commerce.product.domain.aggregate;


import org.micro.commerce.product.domain.event.Event;
import org.micro.commerce.product.domain.exception.VersionMismatch;
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
        Long latestEventVersion = latestEventVersion();
        if (event.getVersion() <= latestEventVersion){
            throw new VersionMismatch(String.format("Event Version Not Consistent -> current event version: %d, latest event version: %d", event.getVersion(), latestEventVersion));
        }
        events.add(event);
        events.stream().forEach(x -> LOGGER.info(String.format("Aggregate EventList eventType: %s, version: %s", x.getEventType(), x.getVersion())));
    }

    public List<T> getEvents() {
        return events;
    }

    public void setEvents(List<T> events) {
        this.events = events;
    }

    public Long latestEventVersion(){
        return events
                .stream()
                .map(event -> event.getVersion())
                .max(Comparator.naturalOrder())
                .orElse(0L);
    }

}
