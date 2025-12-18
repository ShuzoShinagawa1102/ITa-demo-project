package com.demo.shop.infrastructure.persistence.jpa;

import com.demo.shop.domain.identity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);
}

