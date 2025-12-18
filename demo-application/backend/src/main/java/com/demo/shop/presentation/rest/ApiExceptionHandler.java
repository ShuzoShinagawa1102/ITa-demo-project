package com.demo.shop.presentation.rest;

import com.demo.shop.domain.shared.DomainException;
import com.demo.shop.infrastructure.security.InvalidTokenException;
import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(DomainException.class)
  ResponseEntity<?> handleDomain(DomainException e) {
    return ResponseEntity.badRequest().body(error("bad_request", e.getMessage()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
    return ResponseEntity.badRequest().body(error("bad_request", e.getMessage()));
  }

  @ExceptionHandler(InvalidTokenException.class)
  ResponseEntity<?> handleInvalidToken(InvalidTokenException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error("unauthorized", e.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<?> handleUnexpected(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("internal_error", "unexpected error"));
  }

  private Map<String, Object> error(String code, String message) {
    return Map.of(
        "error", code,
        "message", message,
        "timestamp", Instant.now().toString()
    );
  }
}

