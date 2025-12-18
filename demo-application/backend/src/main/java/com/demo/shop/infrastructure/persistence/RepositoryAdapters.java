package com.demo.shop.infrastructure.persistence;

import com.demo.shop.domain.cart.Cart;
import com.demo.shop.domain.cart.CartRepository;
import com.demo.shop.domain.catalog.Product;
import com.demo.shop.domain.catalog.ProductRepository;
import com.demo.shop.domain.identity.User;
import com.demo.shop.domain.identity.UserRepository;
import com.demo.shop.domain.order.Order;
import com.demo.shop.domain.order.OrderRepository;
import com.demo.shop.infrastructure.persistence.jpa.JpaCartRepository;
import com.demo.shop.infrastructure.persistence.jpa.JpaOrderRepository;
import com.demo.shop.infrastructure.persistence.jpa.JpaProductRepository;
import com.demo.shop.infrastructure.persistence.jpa.JpaUserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryAdapters {
  @Bean
  UserRepository userRepository(JpaUserRepository jpa) {
    return new UserRepository() {
      @Override
      public Optional<User> findById(UUID id) {
        return jpa.findById(id);
      }

      @Override
      public Optional<User> findByEmail(String email) {
        return jpa.findByEmail(email);
      }

      @Override
      public User save(User user) {
        return jpa.save(user);
      }
    };
  }

  @Bean
  ProductRepository productRepository(JpaProductRepository jpa) {
    return new ProductRepository() {
      @Override
      public List<Product> findAllActive() {
        return jpa.findByActiveTrueOrderByCreatedAtAsc();
      }

      @Override
      public Optional<Product> findById(UUID id) {
        return jpa.findById(id);
      }
    };
  }

  @Bean
  CartRepository cartRepository(JpaCartRepository jpa) {
    return new CartRepository() {
      @Override
      public Optional<Cart> findByUserId(UUID userId) {
        return jpa.findByUserId(userId);
      }

      @Override
      public Cart save(Cart cart) {
        return jpa.save(cart);
      }
    };
  }

  @Bean
  OrderRepository orderRepository(JpaOrderRepository jpa) {
    return new OrderRepository() {
      @Override
      public Order save(Order order) {
        return jpa.save(order);
      }

      @Override
      public List<Order> findByUserIdOrderByCreatedAtDesc(UUID userId) {
        return jpa.findByUserIdOrderByCreatedAtDesc(userId);
      }
    };
  }
}

