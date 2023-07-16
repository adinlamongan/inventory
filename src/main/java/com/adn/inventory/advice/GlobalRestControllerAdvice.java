package com.adn.inventory.advice;


import com.adn.inventory.exceptions.ResourceInfoException;
import com.adn.inventory.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalRestControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        Map<String, Object> errorRespone = new HashMap<>();
        errorRespone.put("message", ex.getMessage());
        return new ResponseEntity<>(errorRespone, new HttpHeaders(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ResourceInfoException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceInfoException ex,WebRequest request) {
        Map<String, Object> errorRespone = new HashMap<>();
        errorRespone.put("message", ex.getMessage());
        return new ResponseEntity<>(errorRespone, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
