package org.micro.commerce.product.domain.exception;

public class EventRevisionMismatch extends RuntimeException {

    public EventRevisionMismatch(String message) {
        super(message);
    }

}
