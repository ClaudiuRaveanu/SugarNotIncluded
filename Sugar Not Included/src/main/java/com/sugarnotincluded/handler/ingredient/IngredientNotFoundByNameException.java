package com.sugarnotincluded.handler.ingredient;

public class IngredientNotFoundByNameException extends RuntimeException {

    private final String name;

    public IngredientNotFoundByNameException(String name) {
        super("Ingredient with name '" + name + "' not found");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
