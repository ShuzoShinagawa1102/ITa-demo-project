package com.demo.shop.infrastructure.persistence.jpa;

import com.demo.shop.domain.cart.Cart;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCartRepository extends JpaRepository<Cart, UUID> {
  Optional<Cart> findByUserId(UUID userId);
}

