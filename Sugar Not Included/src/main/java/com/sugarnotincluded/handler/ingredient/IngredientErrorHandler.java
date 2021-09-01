package com.sugarnotincluded.handler.ingredient;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class IngredientErrorHandler {

    @ExceptionHandler(IngredientNotFoundByIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String ingredientNotFoundById(IngredientNotFoundByIdException exception, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        return "Couldn't find Ingredient with ID " + exception.getId();
    }

    @ExceptionHandler(IngredientNotFoundByNameException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String ingredientNotFoundByName(IngredientNotFoundByNameException exception, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        return "Couldn't find Ingredient with name '" + exception.getName() + "'";
    }
}
