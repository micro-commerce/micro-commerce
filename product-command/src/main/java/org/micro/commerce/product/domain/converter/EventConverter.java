package org.micro.commerce.product.domain.converter;

import org.micro.commerce.product.domain.event.Event;

public interface EventConverter<T extends Event, K> {

    T toSame(Event<K> event);

    T toNew(Event<K> event);

}
