package com.sugarnotincluded.handler.beverage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class BeverageErrorHandler {

    @ExceptionHandler(BeverageNotFoundByIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String beverageNotFoundById(BeverageNotFoundByIdException exception, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        return "Couldn't find Beverage with ID " + exception.getId();
    }

    @ExceptionHandler(BeverageNotFoundByNameException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String beverageNotFoundByName(BeverageNotFoundByNameException exception, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        return "Couldn't find Beverage with name '" + exception.getName() + "'";
    }
}
