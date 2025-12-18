package com.demo.shop.presentation.rest.dto;

import java.util.UUID;

public final class ProductResponses {
  private ProductResponses() {}

  public record ProductResponse(UUID id, String sku, String name, String description, long priceYen) {}
}

