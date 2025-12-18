package com.demo.shop.domain.order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private OrderStatus status;

  @Column(name = "total_yen", nullable = false)
  private long totalYen;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<OrderItem> items = new ArrayList<>();

  protected Order() {}

  public static Order place(UUID userId, List<OrderItemDraft> drafts, Instant now) {
    Order order = new Order();
    order.id = UUID.randomUUID();
    order.userId = userId;
    order.status = OrderStatus.PLACED;
    order.createdAt = now;
    order.items = new ArrayList<>();

    long total = 0;
    for (OrderItemDraft draft : drafts) {
      OrderItem item = OrderItem.create(order, draft.productId(), draft.productName(), draft.unitPriceYen(), draft.quantity());
      order.items.add(item);
      total += item.subtotalYen();
    }
    order.totalYen = total;
    return order;
  }

  public UUID getId() {
    return id;
  }

  public UUID getUserId() {
    return userId;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public long getTotalYen() {
    return totalYen;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public List<OrderItem> getItems() {
    return items;
  }
}

