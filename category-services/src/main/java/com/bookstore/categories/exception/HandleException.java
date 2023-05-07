package com.bookstore.categories.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(ex.getLocalizedMessage());
    }
}
