package com.example.demo.helpdesk.controller;

import com.example.demo.helpdesk.exception.TicketNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.demo.helpdesk.dto.ErrorResponse;
import com.example.demo.helpdesk.exception.EmailAlreadyExistsException;
import com.example.demo.helpdesk.exception.InvalidCredentialsException;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        ErrorResponse response = new ErrorResponse(
                400,
                "Validation failed",
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(
            EmailAlreadyExistsException ex) {

        ErrorResponse response = new ErrorResponse(
                409,
                ex.getMessage(),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(
            InvalidCredentialsException ex) {

        ErrorResponse response = new ErrorResponse(
                401,
                ex.getMessage(),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTicketNotFound(
            TicketNotFoundException ex) {

        ErrorResponse response = new ErrorResponse(
                404,
                ex.getMessage(),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}