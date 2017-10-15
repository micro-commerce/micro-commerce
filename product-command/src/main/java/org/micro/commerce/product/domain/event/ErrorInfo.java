package org.micro.commerce.product.domain.event;

public class ErrorInfo {

    private String name;
    private String message;

    public ErrorInfo() {
    }

    public ErrorInfo(Exception exception) {
        this.name = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
