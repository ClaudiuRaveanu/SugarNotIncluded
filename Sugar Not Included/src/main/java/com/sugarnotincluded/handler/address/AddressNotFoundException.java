package com.sugarnotincluded.handler.address;

public class AddressNotFoundException extends RuntimeException {

    private final Long id;

    public AddressNotFoundException(Long id) {
        super("Address with ID " + id + " not found");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
