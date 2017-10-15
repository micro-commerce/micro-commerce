package org.micro.commerce.product.domain.converter;

import org.micro.commerce.product.domain.event.Event;
import org.micro.commerce.product.domain.event.ProductEvent;
import org.micro.commerce.product.domain.model.Product;

import java.lang.reflect.ParameterizedType;
import java.util.UUID;

public abstract class ProductEventConverter<T extends ProductEvent> implements EventConverter<T, Product> {

    private Class<T> type;

    public ProductEventConverter() {
        this.type = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T toSame(Event<Product> event) {
        T resultEvent = createNew();
        resultEvent.setEventId(event.getEventId());
        resultEvent.setTraceId(event.getTraceId());
        resultEvent.setVersion(event.getVersion());
        resultEvent.setModel(event.getModel());
        return resultEvent;
    }

    @Override
    public T toNew(Event<Product> event) {
        T resultEvent = createNew();
        resultEvent.setEventId(UUID.randomUUID());
        resultEvent.setTraceId(event.getTraceId());
        resultEvent.setVersion(event.getVersion() + 1);
        resultEvent.setModel(event.getModel());
        return resultEvent;
    }

    private T createNew(){
        try {
            return this.type.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
