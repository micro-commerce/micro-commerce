package org.micro.commerce.product.infrastructure.adapter.writer;

public interface Writer<T> {

    void persist(T event);

}
