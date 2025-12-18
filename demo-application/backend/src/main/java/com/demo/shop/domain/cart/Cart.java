package com.demo.shop.domain.cart;

import com.demo.shop.domain.shared.Preconditions;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "carts")
public class Cart {
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "user_id", nullable = false, unique = true)
  private UUID userId;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<CartItem> items = new ArrayList<>();

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  protected Cart() {}

  public Cart(UUID id, UUID userId, Instant now) {
    this.id = id;
    this.userId = userId;
    this.createdAt = now;
    this.updatedAt = now;
  }

  public UUID getId() {
    return id;
  }

  public UUID getUserId() {
    return userId;
  }

  public List<CartItem> getItems() {
    return items;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void addOrIncrease(UUID productId, long unitPriceYen, int quantity, Instant now) {
    Preconditions.require(quantity > 0, "quantity must be > 0");
    Preconditions.require(unitPriceYen >= 0, "unitPriceYen must be >= 0");

    Optional<CartItem> existing = items.stream().filter(i -> i.getProductId().equals(productId)).findFirst();
    if (existing.isPresent()) {
      existing.get().increase(quantity, now);
    } else {
      items.add(CartItem.create(this, productId, unitPriceYen, quantity, now));
    }
    this.updatedAt = now;
  }

  public void changeQuantity(UUID itemId, int quantity, Instant now) {
    Preconditions.require(quantity > 0, "quantity must be > 0");
    CartItem item = items.stream().filter(i -> i.getId().equals(itemId)).findFirst()
        .orElseThrow(() -> new IllegalArgumentException("item not found"));
    item.changeQuantity(quantity, now);
    this.updatedAt = now;
  }

  public void removeItem(UUID itemId, Instant now) {
    boolean removed = items.removeIf(i -> i.getId().equals(itemId));
    if (removed) {
      this.updatedAt = now;
    }
  }

  public void clear(Instant now) {
    items.clear();
    this.updatedAt = now;
  }

  public long totalYen() {
    return items.stream().mapToLong(CartItem::subtotalYen).sum();
  }
}

