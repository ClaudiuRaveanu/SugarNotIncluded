package com.sugarnotincluded.handler.beverage;

public class BeverageNotFoundByNameException extends RuntimeException {

    private final String name;

    public BeverageNotFoundByNameException(String name) {
        super("Beverage with name '" + name + "' not found");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
