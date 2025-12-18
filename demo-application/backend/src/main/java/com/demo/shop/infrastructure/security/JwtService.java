package com.demo.shop.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.demo.shop.domain.identity.User;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class JwtService {
  private final JwtProperties props;
  private final Algorithm algorithm;
  private final JWTVerifier verifier;

  public JwtService(JwtProperties props) {
    this.props = props;
    this.algorithm = Algorithm.HMAC256(props.secret());
    this.verifier = JWT.require(algorithm).withIssuer(props.issuer()).build();
  }

  public String issueAccessToken(User user) {
    Instant now = Instant.now();
    Instant exp = now.plus(props.accessTokenMinutes(), ChronoUnit.MINUTES);
    return JWT.create()
        .withIssuer(props.issuer())
        .withSubject(user.getId().toString())
        .withIssuedAt(Date.from(now))
        .withExpiresAt(Date.from(exp))
        .withClaim("email", user.getEmail())
        .withClaim("role", user.getRole().name())
        .sign(algorithm);
  }

  public AuthPrincipal verify(String token) {
    try {
      DecodedJWT jwt = verifier.verify(token);
      UUID userId = UUID.fromString(jwt.getSubject());
      String email = jwt.getClaim("email").asString();
      String role = jwt.getClaim("role").asString();
      return new AuthPrincipal(userId, email, role);
    } catch (JWTVerificationException e) {
      throw new InvalidTokenException();
    }
  }
}

