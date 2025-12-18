package com.demo.shop.presentation.rest;

import com.demo.shop.application.catalog.CatalogService;
import com.demo.shop.domain.catalog.Product;
import com.demo.shop.presentation.rest.dto.ProductResponses.ProductResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final CatalogService catalogService;

  public ProductController(CatalogService catalogService) {
    this.catalogService = catalogService;
  }

  @GetMapping
  public List<ProductResponse> list() {
    return catalogService.listActiveProducts().stream().map(ProductController::toResponse).toList();
  }

  private static ProductResponse toResponse(Product p) {
    return new ProductResponse(
        p.getId(),
        p.getSku(),
        p.getName(),
        p.getDescription(),
        p.getPriceYen()
    );
  }
}

