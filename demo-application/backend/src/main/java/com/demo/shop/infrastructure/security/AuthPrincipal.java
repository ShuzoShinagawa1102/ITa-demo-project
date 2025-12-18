package com.demo.shop.infrastructure.security;

import java.util.UUID;

public record AuthPrincipal(UUID userId, String email, String role) {}

