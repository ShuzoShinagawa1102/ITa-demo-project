package com.demo.shop.application.auth;

import java.util.UUID;

public record AuthResult(String accessToken, UUID userId, String email, String role) {}

