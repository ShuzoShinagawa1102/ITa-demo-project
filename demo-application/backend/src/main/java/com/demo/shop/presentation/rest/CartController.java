package com.demo.shop.presentation.rest;

import com.demo.shop.application.cart.CartService;
import com.demo.shop.domain.cart.Cart;
import com.demo.shop.domain.cart.CartItem;
import com.demo.shop.domain.catalog.Product;
import com.demo.shop.domain.catalog.ProductRepository;
import com.demo.shop.infrastructure.security.AuthPrincipal;
import com.demo.shop.presentation.rest.dto.CartRequests.AddItemRequest;
import com.demo.shop.presentation.rest.dto.CartRequests.ChangeQuantityRequest;
import com.demo.shop.presentation.rest.dto.CartResponses.CartItemResponse;
import com.demo.shop.presentation.rest.dto.CartResponses.CartResponse;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.UUID;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {
  private final CartService cartService;
  private final ProductRepository productRepository;

  public CartController(CartService cartService, ProductRepository productRepository) {
    this.cartService = cartService;
    this.productRepository = productRepository;
  }

  @GetMapping
  public CartResponse get(@AuthenticationPrincipal AuthPrincipal principal) {
    Cart cart = cartService.getOrCreate(principal.userId());
    return toResponse(cart);
  }

  @PostMapping("/items")
  public CartResponse add(@AuthenticationPrincipal AuthPrincipal principal, @Valid @RequestBody AddItemRequest req) {
    Cart cart = cartService.addItem(principal.userId(), req.productId(), req.quantity());
    return toResponse(cart);
  }

  @PutMapping("/items/{itemId}")
  public CartResponse changeQty(@AuthenticationPrincipal AuthPrincipal principal, @PathVariable UUID itemId, @Valid @RequestBody ChangeQuantityRequest req) {
    Cart cart = cartService.changeQuantity(principal.userId(), itemId, req.quantity());
    return toResponse(cart);
  }

  @DeleteMapping("/items/{itemId}")
  public CartResponse remove(@AuthenticationPrincipal AuthPrincipal principal, @PathVariable UUID itemId) {
    Cart cart = cartService.removeItem(principal.userId(), itemId);
    return toResponse(cart);
  }

  private CartResponse toResponse(Cart cart) {
    Map<UUID, Product> productById = productRepository.findAllActive().stream().collect(java.util.stream.Collectors.toMap(Product::getId, p -> p));
    return new CartResponse(
        cart.getId(),
        cart.getUserId(),
        cart.getItems().stream().map(i -> toItemResponse(i, productById.get(i.getProductId()))).toList(),
        cart.totalYen()
    );
  }

  private CartItemResponse toItemResponse(CartItem item, Product productOrNull) {
    String productName = productOrNull == null ? null : productOrNull.getName();
    return new CartItemResponse(
        item.getId(),
        item.getProductId(),
        productName,
        item.getUnitPriceYen(),
        item.getQuantity(),
        item.getUnitPriceYen() * (long) item.getQuantity()
    );
  }
}

