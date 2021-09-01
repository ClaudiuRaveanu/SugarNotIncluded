package com.sugarnotincluded.handler.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class CustomerErrorHandler {

    @ExceptionHandler(CustomerNotFoundByIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String customerNotFoundByIdException(CustomerNotFoundByIdException exception, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        return "Couldn't find Customer with ID " + exception.getId();
    }

    @ExceptionHandler(CustomerNotFoundByNameException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String customerNotFoundByNameException(CustomerNotFoundByNameException exception, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        return "Couldn't find Customer with name '" + exception.getName() + "'";
    }
}
