package com.demo.shop.presentation.rest.dto;

import java.util.List;
import java.util.UUID;

public final class CartResponses {
  private CartResponses() {}

  public record CartResponse(UUID cartId, UUID userId, List<CartItemResponse> items, long totalYen) {}

  public record CartItemResponse(UUID itemId, UUID productId, String productName, long unitPriceYen, int quantity, long subtotalYen) {}
}

