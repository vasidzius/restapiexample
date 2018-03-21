package com.vasidzius.restapiexample.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.RollbackException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { RollbackException.class })
    protected ResponseEntity<Object> handleConflict(RollbackException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getCause().getMessage(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
