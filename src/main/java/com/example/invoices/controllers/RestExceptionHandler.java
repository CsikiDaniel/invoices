package com.example.invoices.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse() {
            @Override
            public HttpStatusCode getStatusCode() {
                return HttpStatusCode.valueOf(404);
            }

            @Override
            public HttpHeaders getHeaders() {
                return ErrorResponse.super.getHeaders();
            }

            @Override
            public ProblemDetail getBody() {
                return null;
            }

            @Override
            public String getDetailMessageCode() {
                return ErrorResponse.super.getDetailMessageCode();
            }

            @Override
            public Object[] getDetailMessageArguments() {
                return ErrorResponse.super.getDetailMessageArguments();
            }

            @Override
            public Object[] getDetailMessageArguments(MessageSource messageSource, Locale locale) {
                return ErrorResponse.super.getDetailMessageArguments(messageSource, locale);
            }

            @Override
            public String getTitleMessageCode() {
                return ErrorResponse.super.getTitleMessageCode();
            }


            @Override
            public ProblemDetail updateAndGetBody(MessageSource messageSource, Locale locale) {
                return ErrorResponse.super.updateAndGetBody(messageSource, locale);
            }

        };
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}