package com.demo.shop.presentation.rest;

import com.demo.shop.application.auth.AuthResult;
import com.demo.shop.application.auth.AuthService;
import com.demo.shop.infrastructure.security.AuthPrincipal;
import com.demo.shop.presentation.rest.dto.AuthRequests.LoginRequest;
import com.demo.shop.presentation.rest.dto.AuthRequests.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public AuthResult register(@Valid @RequestBody RegisterRequest req) {
    return authService.register(req.email(), req.password());
  }

  @PostMapping("/login")
  public AuthResult login(@Valid @RequestBody LoginRequest req) {
    return authService.login(req.email(), req.password());
  }

  @GetMapping("/me")
  public Object me(@AuthenticationPrincipal AuthPrincipal principal) {
    return principal;
  }
}

