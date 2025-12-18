package com.demo.shop.domain.catalog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
  List<Product> findAllActive();

  Optional<Product> findById(UUID id);
}

