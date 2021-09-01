package com.sugarnotincluded.handler.order;

public class OrderNotFoundException extends RuntimeException {

    private final Long id;

    public OrderNotFoundException(Long id) {
        super("Order with ID " + id + " not found");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
