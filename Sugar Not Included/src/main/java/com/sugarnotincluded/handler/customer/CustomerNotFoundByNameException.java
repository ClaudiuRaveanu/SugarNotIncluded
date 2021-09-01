package com.sugarnotincluded.handler.customer;

public class CustomerNotFoundByNameException extends RuntimeException {

    private final String name;

    public CustomerNotFoundByNameException(String name) {
        super("Customer with name '" + name + "' not found");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
