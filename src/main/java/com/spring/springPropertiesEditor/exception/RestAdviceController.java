package com.spring.springPropertiesEditor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;

@ControllerAdvice
public class RestAdviceController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({FileNotFoundException.class})
    public ResponseEntity<String> handleFileNotFound(Exception e) {
        return new ResponseEntity<>("User file not found! " + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<String> handleIOException(Exception e) {
        return new ResponseEntity<>("Cannot read the file! " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
