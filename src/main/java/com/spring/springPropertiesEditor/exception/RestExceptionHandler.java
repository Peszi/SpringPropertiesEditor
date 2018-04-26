package com.spring.springPropertiesEditor.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({FileNotFoundException.class})
    public ResponseEntity<String> handleFileNotFound(Exception e) {
        return new ResponseEntity<>("User file not found!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<String> handleIOException(Exception e) {
        return new ResponseEntity<>("Cannot read the file!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
