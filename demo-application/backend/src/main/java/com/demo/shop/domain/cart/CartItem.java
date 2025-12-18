package com.demo.shop.domain.cart;

import com.demo.shop.domain.shared.Preconditions;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "cart_items")
public class CartItem {
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id", nullable = false)
  private Cart cart;

  @Column(name = "product_id", nullable = false)
  private UUID productId;

  @Column(name = "quantity", nullable = false)
  private int quantity;

  @Column(name = "unit_price_yen", nullable = false)
  private long unitPriceYen;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  protected CartItem() {}

  static CartItem create(Cart cart, UUID productId, long unitPriceYen, int quantity, Instant now) {
    Preconditions.require(quantity > 0, "quantity must be > 0");
    CartItem item = new CartItem();
    item.id = UUID.randomUUID();
    item.cart = cart;
    item.productId = productId;
    item.quantity = quantity;
    item.unitPriceYen = unitPriceYen;
    item.createdAt = now;
    item.updatedAt = now;
    return item;
  }

  public UUID getId() {
    return id;
  }

  public UUID getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public long getUnitPriceYen() {
    return unitPriceYen;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  void increase(int delta, Instant now) {
    Preconditions.require(delta > 0, "delta must be > 0");
    this.quantity += delta;
    this.updatedAt = now;
  }

  void changeQuantity(int quantity, Instant now) {
    Preconditions.require(quantity > 0, "quantity must be > 0");
    this.quantity = quantity;
    this.updatedAt = now;
  }

  long subtotalYen() {
    return unitPriceYen * (long) quantity;
  }
}

