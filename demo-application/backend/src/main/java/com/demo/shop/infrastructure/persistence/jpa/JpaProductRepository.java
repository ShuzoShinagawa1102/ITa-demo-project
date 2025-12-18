package com.demo.shop.infrastructure.persistence.jpa;

import com.demo.shop.domain.catalog.Product;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product, UUID> {
  List<Product> findByActiveTrueOrderByCreatedAtAsc();
}

