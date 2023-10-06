package com.abdelhakim.cnc.login.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.abdelhakim.cnc.login.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  //@Value("${abdelhakim.app.jwtSecret}")
  private final String jwtSecret = "qFVFzWHpdEWBMjAwp9oZtvAqm122OWyv2cCu87cN0YArqJk1Z1kukGmOJkX9deBU43WyzWhvRegYNQrj7mQmj5";


  @Value("${abdelhakim.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${abdelhakim.app.jwtCookieName}")
  private String jwtCookie;

  public String getJwtFromCookies(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
    if (cookie != null) {
      return cookie.getValue();
    } else {
      return null;
    }
  }

  public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
    String jwt = generateJwtToken(userPrincipal);
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt)
            .path("/api")
            .maxAge(24 * 60 * 60)
            .httpOnly(true)
            .build();
    return cookie;
  }

  public ResponseCookie getCleanJwtCookie() {
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, null)
            .path("/api")
            .build();
    return cookie;
  }

  public String getUserNameFromJwtToken(String token) {
    SecretKey secret = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    Claims claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    System.out.println("Token Claims: " + claims);
    return claims.getSubject();
  }


  public String generateJwtToken(UserDetailsImpl userPrincipal) {
    return Jwts.builder()
            .setSubject(userPrincipal.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .claim("roles", userPrincipal.getAuthorities()) // Include roles in claims
            .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
            .compact();
  }


  public boolean validateJwtToken(String authToken) {
    if (authToken == null || authToken.isEmpty()) {
      logger.error("JWT token is null or empty");
      return false;
    }

    logger.debug("JWT token received: {}", authToken);

    try {
      SecretKey secret = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
      Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(authToken);
      logger.debug("JWT token signature verified successfully.");
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }



  public String generateTokenFromUsername(String username) {
    SecretKey secret = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(secret)
            .compact();
  }

}
