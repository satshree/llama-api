package com.llama.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class GlobalExceptionResponse {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionResponse(Exception ex, WebRequest req) {
        Error err = new Error(
                ex.getMessage(),
                Stream.of(
                        req.getDescription(false)
                ).collect(
                        Collectors.toCollection(ArrayList::new)
                ),
                new Date()
        );

        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> resourceNotFoundResponse(ResourceNotFound ex, WebRequest req) {
        Error err = new Error(
                ex.getMessage(),
                Stream.of(
                        req.getDescription(false)
                ).collect(
                        Collectors.toCollection(ArrayList::new)
                ),
                new Date()
        );

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> authenticationFailedResponse(AuthenticationException ex, WebRequest req) {
        Error err = new Error(
                ex.getMessage(),
                Stream.of(
                        req.getDescription(false)
                ).collect(
                        Collectors.toCollection(ArrayList::new)
                ),
                new Date()
        );

        return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> methodArgNotValidResponse(MethodArgumentNotValidException ex, WebRequest req) {
        List<String> allDetails = new ArrayList<>();
        allDetails.add(req.getDescription(false));

        ex.getBindingResult().getAllErrors().forEach(e -> allDetails.add(e.getDefaultMessage()));

        Error err = new Error(
                ex.getMessage(),
                Stream.of(
                        req.getDescription(false)
                ).collect(
                        Collectors.toCollection(ArrayList::new)
                ),
                new Date()
        );

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}
