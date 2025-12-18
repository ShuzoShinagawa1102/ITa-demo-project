package com.demo.shop.domain.order;

import java.util.UUID;

public record OrderItemDraft(UUID productId, String productName, long unitPriceYen, int quantity) {}

