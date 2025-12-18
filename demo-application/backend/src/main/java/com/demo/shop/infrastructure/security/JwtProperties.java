package com.demo.shop.infrastructure.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security.jwt")
public record JwtProperties(
    String issuer,
    String secret,
    long accessTokenMinutes
) {}

