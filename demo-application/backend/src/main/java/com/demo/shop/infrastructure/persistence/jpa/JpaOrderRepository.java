package com.demo.shop.infrastructure.persistence.jpa;

import com.demo.shop.domain.order.Order;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<Order, UUID> {
  List<Order> findByUserIdOrderByCreatedAtDesc(UUID userId);
}

