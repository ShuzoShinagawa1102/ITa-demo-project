package com.demo.shop.application.auth;

import com.demo.shop.application.TimeProvider;
import com.demo.shop.domain.identity.User;
import com.demo.shop.domain.identity.UserRepository;
import com.demo.shop.domain.identity.UserRole;
import com.demo.shop.domain.shared.DomainException;
import com.demo.shop.infrastructure.security.JwtService;
import java.util.Locale;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final TimeProvider timeProvider;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, TimeProvider timeProvider) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.timeProvider = timeProvider;
  }

  @Transactional
  public AuthResult register(String email, String rawPassword) {
    String normalizedEmail = normalizeEmail(email);
    if (userRepository.findByEmail(normalizedEmail).isPresent()) {
      throw new DomainException("email already registered");
    }
    String passwordHash = passwordEncoder.encode(rawPassword);
    User user = new User(UUID.randomUUID(), normalizedEmail, passwordHash, UserRole.USER, timeProvider.now());
    userRepository.save(user);
    String token = jwtService.issueAccessToken(user);
    return new AuthResult(token, user.getId(), user.getEmail(), user.getRole().name());
  }

  @Transactional(readOnly = true)
  public AuthResult login(String email, String rawPassword) {
    String normalizedEmail = normalizeEmail(email);
    User user = userRepository.findByEmail(normalizedEmail)
        .orElseThrow(() -> new DomainException("invalid email or password"));
    if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
      throw new DomainException("invalid email or password");
    }
    String token = jwtService.issueAccessToken(user);
    return new AuthResult(token, user.getId(), user.getEmail(), user.getRole().name());
  }

  private String normalizeEmail(String email) {
    if (email == null) {
      return null;
    }
    return email.trim().toLowerCase(Locale.ROOT);
  }
}

