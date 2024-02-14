package com.example.learnspringboot;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationError(ConstraintViolationException exception) {
        String errors = exception.getMessage();

        Map<String, Object> errorBody = Map.ofEntries(Map.entry("status", "fail"), Map.entry("message", errors));
        return ResponseEntity.badRequest().body(errorBody);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNoSuchElementException(NoSuchElementException exception) {
        String error = exception.getMessage();

        Map<String, Object> errorBody = Map.ofEntries(Map.entry("status", "fail"), Map.entry("message", error));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String error = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

        Map<String, Object> errorBody = Map.ofEntries(Map.entry("status", "fail"), Map.entry("message", error));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }
}
