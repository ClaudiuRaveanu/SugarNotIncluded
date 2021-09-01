package com.sugarnotincluded.handler.recipe;

public class RecipeNotFoundException extends RuntimeException {

    private final Long id;

    public RecipeNotFoundException(Long id) {
        super("Recipe with ID " + id + " not found.");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
