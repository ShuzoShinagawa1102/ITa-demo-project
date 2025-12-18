package com.demo.shop.domain.order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {
  Order save(Order order);

  List<Order> findByUserIdOrderByCreatedAtDesc(UUID userId);
}

