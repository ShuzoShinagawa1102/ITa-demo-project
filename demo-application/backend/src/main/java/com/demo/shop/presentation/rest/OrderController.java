package com.demo.shop.presentation.rest;

import com.demo.shop.application.order.OrderService;
import com.demo.shop.domain.order.Order;
import com.demo.shop.domain.order.OrderItem;
import com.demo.shop.infrastructure.security.AuthPrincipal;
import com.demo.shop.presentation.rest.dto.OrderResponses.OrderItemResponse;
import com.demo.shop.presentation.rest.dto.OrderResponses.OrderResponse;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/checkout")
  public OrderResponse checkout(@AuthenticationPrincipal AuthPrincipal principal) {
    return toResponse(orderService.checkout(principal.userId()));
  }

  @GetMapping
  public List<OrderResponse> list(@AuthenticationPrincipal AuthPrincipal principal) {
    return orderService.listOrders(principal.userId()).stream().map(OrderController::toResponse).toList();
  }

  private static OrderResponse toResponse(Order order) {
    return new OrderResponse(
        order.getId(),
        order.getStatus().name(),
        order.getTotalYen(),
        order.getCreatedAt(),
        order.getItems().stream().map(OrderController::toItemResponse).toList()
    );
  }

  private static OrderItemResponse toItemResponse(OrderItem item) {
    return new OrderItemResponse(
        item.getId(),
        item.getProductId(),
        item.getProductName(),
        item.getUnitPriceYen(),
        item.getQuantity(),
        item.getUnitPriceYen() * (long) item.getQuantity()
    );
  }
}

