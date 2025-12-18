package com.demo.shop.domain.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "sku", nullable = false, unique = true)
  private String sku;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "price_yen", nullable = false)
  private long priceYen;

  @Column(name = "active", nullable = false)
  private boolean active;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  protected Product() {}

  public Product(UUID id, String sku, String name, String description, long priceYen, boolean active, Instant createdAt) {
    this.id = id;
    this.sku = sku;
    this.name = name;
    this.description = description;
    this.priceYen = priceYen;
    this.active = active;
    this.createdAt = createdAt;
  }

  public UUID getId() {
    return id;
  }

  public String getSku() {
    return sku;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public long getPriceYen() {
    return priceYen;
  }

  public boolean isActive() {
    return active;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}

