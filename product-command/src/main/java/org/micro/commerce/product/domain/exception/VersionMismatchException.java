package org.micro.commerce.product.domain.exception;

public class VersionMismatchException extends RuntimeException {

    public VersionMismatchException(String message) {
        super(message);
    }

}
