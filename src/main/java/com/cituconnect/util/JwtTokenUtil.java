package com.cituconnect.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil {

    @Value("${app.jwt.secret:change_this_to_at_least_32_chars_123456}")
    private String jwtSecret;

    @Value("${app.jwt.expirationInMs:86400000}")
    private long jwtExpirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, Long userId, String name) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + jwtExpirationMs);
        return Jwts.builder()
                .setSubject(email)
                .setClaims(Map.of("userId", userId, "name", name, "email", email))
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public Long getUserIdFromToken(String token) {
        try {
            Object userIdObj = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("userId");

            if (userIdObj instanceof Integer) {
                return ((Integer) userIdObj).longValue();
            } else if (userIdObj instanceof Long) {
                return (Long) userIdObj;
            } else if (userIdObj instanceof String) {
                return Long.parseLong((String) userIdObj);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("[DEBUG] Failed to parse userId from token: " + e.getMessage());
            return null;
        }
    }
}
