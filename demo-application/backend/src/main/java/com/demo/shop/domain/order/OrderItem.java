package com.demo.shop.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class OrderItem {
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Column(name = "product_id", nullable = false)
  private UUID productId;

  @Column(name = "product_name", nullable = false)
  private String productName;

  @Column(name = "unit_price_yen", nullable = false)
  private long unitPriceYen;

  @Column(name = "quantity", nullable = false)
  private int quantity;

  protected OrderItem() {}

  static OrderItem create(Order order, UUID productId, String productName, long unitPriceYen, int quantity) {
    OrderItem item = new OrderItem();
    item.id = UUID.randomUUID();
    item.order = order;
    item.productId = productId;
    item.productName = productName;
    item.unitPriceYen = unitPriceYen;
    item.quantity = quantity;
    return item;
  }

  public UUID getId() {
    return id;
  }

  public UUID getProductId() {
    return productId;
  }

  public String getProductName() {
    return productName;
  }

  public long getUnitPriceYen() {
    return unitPriceYen;
  }

  public int getQuantity() {
    return quantity;
  }

  long subtotalYen() {
    return unitPriceYen * (long) quantity;
  }
}

