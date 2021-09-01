package com.sugarnotincluded.handler.customer;

public class CustomerNotFoundByIdException extends RuntimeException {

    private final Long id;

    public CustomerNotFoundByIdException(Long id) {
        super("Customer with ID " + id + " not found");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
