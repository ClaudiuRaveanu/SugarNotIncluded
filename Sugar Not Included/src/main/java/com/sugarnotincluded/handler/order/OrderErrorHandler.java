package com.sugarnotincluded.handler.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class OrderErrorHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String orderNotFoundException(OrderNotFoundException exception, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        return "Couldn't find Order with ID " + exception.getId();
    }
}
