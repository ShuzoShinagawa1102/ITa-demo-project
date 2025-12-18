package com.demo.shop.domain.shared;

public final class Preconditions {
  private Preconditions() {}

  public static void require(boolean condition, String message) {
    if (!condition) {
      throw new DomainException(message);
    }
  }
}

