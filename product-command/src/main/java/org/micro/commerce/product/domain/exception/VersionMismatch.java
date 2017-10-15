package org.micro.commerce.product.domain.exception;

public class VersionMismatch extends RuntimeException {

    public VersionMismatch(String message) {
        super(message);
    }

}
