package com.sugarnotincluded.handler.beverage;

public class BeverageNotFoundByIdException extends RuntimeException {

    private final Long id;

    public BeverageNotFoundByIdException(Long id) {
        super("Beverage with ID " + id + " not found.");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
