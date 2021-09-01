package com.sugarnotincluded.handler.recipe;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class RecipeErrorHandler {

    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String recipeNotFound(RecipeNotFoundException exception, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        return "Couldn't find Recipe with ID " + exception.getId();
    }
}
