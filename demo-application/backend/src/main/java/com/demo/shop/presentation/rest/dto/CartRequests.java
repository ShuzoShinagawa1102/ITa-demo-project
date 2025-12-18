package com.demo.shop.presentation.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public final class CartRequests {
  private CartRequests() {}

  public record AddItemRequest(@NotNull UUID productId, @Min(1) @Max(99) int quantity) {}

  public record ChangeQuantityRequest(@Min(1) @Max(99) int quantity) {}
}
