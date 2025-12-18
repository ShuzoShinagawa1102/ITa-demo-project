package com.demo.shop.presentation.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public final class AuthRequests {
  private AuthRequests() {}

  public record RegisterRequest(
      @Email @NotBlank @Size(max = 255) String email,
      @NotBlank @Size(min = 8, max = 72) @Pattern(regexp = "^\\S+$", message = "password must not contain whitespace") String password
  ) {}

  public record LoginRequest(
      @Email @NotBlank @Size(max = 255) String email,
      @NotBlank @Size(min = 8, max = 72) @Pattern(regexp = "^\\S+$", message = "password must not contain whitespace") String password
  ) {}
}
