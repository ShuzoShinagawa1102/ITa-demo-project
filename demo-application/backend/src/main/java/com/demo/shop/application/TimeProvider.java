package com.demo.shop.application;

import java.time.Clock;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class TimeProvider {
  private final Clock clock = Clock.systemUTC();

  public Instant now() {
    return Instant.now(clock);
  }
}

