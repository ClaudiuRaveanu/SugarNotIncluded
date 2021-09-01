package com.sugarnotincluded.handler.ingredient;

public class IngredientNotFoundByIdException extends RuntimeException {

    private final Long id;

    public IngredientNotFoundByIdException(Long id) {
        super ("Ingredient with ID " + id + " not found.");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
