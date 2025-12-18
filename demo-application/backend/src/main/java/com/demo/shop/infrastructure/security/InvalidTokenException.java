package com.demo.shop.infrastructure.security;

public class InvalidTokenException extends RuntimeException {
  public InvalidTokenException() {
    super("invalid token");
  }
}

