package com.deviget.minesweeper.controller.exception;

import com.deviget.minesweeper.exception.ResourceNotFoundException;
import com.deviget.minesweeper.exception.ValidationException;
import com.deviget.minesweeper.model.api.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class MainExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(MainExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> illegalArgumentException(IllegalArgumentException e) {
        logger.warn("Illegal arguments", e);
        var apiError = new ApiErrorResponse("ILLEGAL_ARGUMENTS", "Request contains illegal arguments",
                List.of(e.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> resourceNotFoundException(ResourceNotFoundException e) {
        logger.warn("Resource not found", e);
        var apiError = new ApiErrorResponse("RESOURCE_NOT_FOUND", "The requested resource couldn't be found",
                List.of(e.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorResponse> violationException(ValidationException e) {
        logger.warn("Invalid request", e);
        var apiError = new ApiErrorResponse("INVALID_REQUEST", "Request has invalid parameters",
                e.getViolations());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> exception(Exception e) {
        logger.error("An unexpected error occurred", e);
        var apiError = new ApiErrorResponse("UNKNOWN_ERROR", "An unexpected error occurred",
                List.of(e.getMessage()));
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(apiError);
    }
}
