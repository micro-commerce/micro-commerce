package org.micro.commerce.product.domain.aggregate;


import org.micro.commerce.product.domain.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAggregate<T extends Event> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseAggregate.class);

    private List<T> events = new ArrayList<>();

    public void apply(T event){
        events.add(event);
        events.stream().forEach(x -> LOGGER.info("eventList item: " + x.getEventType()));
    }

    public List<T> getEvents() {
        return events;
    }

    public void setEvents(List<T> events) {
        this.events = events;
    }

}
