package com.jijojames.app;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestErrorHandler {
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseBody
    public ResponseEntity<Object> processInvalidFormatException(Exception e) {
        Map error = new HashMap();
        error.put("error-message", "Invalid format exception");
        error.put("error-code", 400);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
