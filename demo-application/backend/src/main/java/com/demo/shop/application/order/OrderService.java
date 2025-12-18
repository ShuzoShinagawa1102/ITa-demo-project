package com.demo.shop.application.order;

import com.demo.shop.application.TimeProvider;
import com.demo.shop.domain.cart.Cart;
import com.demo.shop.domain.cart.CartItem;
import com.demo.shop.domain.cart.CartRepository;
import com.demo.shop.domain.catalog.Product;
import com.demo.shop.domain.catalog.ProductRepository;
import com.demo.shop.domain.order.Order;
import com.demo.shop.domain.order.OrderItemDraft;
import com.demo.shop.domain.order.OrderRepository;
import com.demo.shop.domain.shared.DomainException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final TimeProvider timeProvider;

  public OrderService(OrderRepository orderRepository, CartRepository cartRepository, ProductRepository productRepository, TimeProvider timeProvider) {
    this.orderRepository = orderRepository;
    this.cartRepository = cartRepository;
    this.productRepository = productRepository;
    this.timeProvider = timeProvider;
  }

  @Transactional
  public Order checkout(UUID userId) {
    Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new DomainException("cart is empty"));
    if (cart.getItems().isEmpty()) {
      throw new DomainException("cart is empty");
    }

    List<OrderItemDraft> drafts = new ArrayList<>();
    for (CartItem item : cart.getItems()) {
      Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> new DomainException("product not found"));
      drafts.add(new OrderItemDraft(product.getId(), product.getName(), item.getUnitPriceYen(), item.getQuantity()));
    }

    Order order = Order.place(userId, drafts, timeProvider.now());
    Order saved = orderRepository.save(order);

    cart.clear(timeProvider.now());
    cartRepository.save(cart);
    return saved;
  }

  @Transactional(readOnly = true)
  public List<Order> listOrders(UUID userId) {
    return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
  }
}

