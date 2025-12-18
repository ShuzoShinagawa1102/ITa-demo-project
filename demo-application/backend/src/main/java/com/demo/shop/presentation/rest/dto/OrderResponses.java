package com.demo.shop.presentation.rest.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class OrderResponses {
  private OrderResponses() {}

  public record OrderResponse(UUID orderId, String status, long totalYen, Instant createdAt, List<OrderItemResponse> items) {}

  public record OrderItemResponse(UUID itemId, UUID productId, String productName, long unitPriceYen, int quantity, long subtotalYen) {}
}

