package com.demo.shop.application.cart;

import com.demo.shop.application.TimeProvider;
import com.demo.shop.domain.cart.Cart;
import com.demo.shop.domain.cart.CartRepository;
import com.demo.shop.domain.catalog.Product;
import com.demo.shop.domain.catalog.ProductRepository;
import com.demo.shop.domain.shared.DomainException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final TimeProvider timeProvider;

  public CartService(CartRepository cartRepository, ProductRepository productRepository, TimeProvider timeProvider) {
    this.cartRepository = cartRepository;
    this.productRepository = productRepository;
    this.timeProvider = timeProvider;
  }

  @Transactional
  public Cart getOrCreate(UUID userId) {
    return cartRepository.findByUserId(userId).orElseGet(() -> cartRepository.save(new Cart(UUID.randomUUID(), userId, timeProvider.now())));
  }

  @Transactional
  public Cart addItem(UUID userId, UUID productId, int quantity) {
    Product product = productRepository.findById(productId).orElseThrow(() -> new DomainException("product not found"));
    if (!product.isActive()) {
      throw new DomainException("product is not available");
    }

    Cart cart = getOrCreate(userId);
    cart.addOrIncrease(productId, product.getPriceYen(), quantity, timeProvider.now());
    return cartRepository.save(cart);
  }

  @Transactional
  public Cart changeQuantity(UUID userId, UUID itemId, int quantity) {
    Cart cart = getOrCreate(userId);
    cart.changeQuantity(itemId, quantity, timeProvider.now());
    return cartRepository.save(cart);
  }

  @Transactional
  public Cart removeItem(UUID userId, UUID itemId) {
    Cart cart = getOrCreate(userId);
    cart.removeItem(itemId, timeProvider.now());
    return cartRepository.save(cart);
  }
}

