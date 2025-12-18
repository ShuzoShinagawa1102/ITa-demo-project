package com.demo.shop.application.catalog;

import com.demo.shop.domain.catalog.Product;
import com.demo.shop.domain.catalog.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogService {
  private final ProductRepository productRepository;

  public CatalogService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Transactional(readOnly = true)
  public List<Product> listActiveProducts() {
    return productRepository.findAllActive();
  }
}

