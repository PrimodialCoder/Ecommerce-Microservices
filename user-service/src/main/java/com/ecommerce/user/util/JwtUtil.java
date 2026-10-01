package com.ecommerce.user.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {
    private final Key key;
    private final long jwtExpirationMs;

    public JwtUtil(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration}") long jwtExpirationMs
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtExpirationMs = jwtExpirationMs;
    }


    //generate jwt token
    public String generateToken(Long userId, String email, String role) {
        long nowMillis = System.currentTimeMillis();
        return Jwts.builder().setSubject(String.valueOf(userId))
                .claims("email", email)
                .claims("role", role)
                .issuedAt(new java.util.Date(nowMillis))
                .expiration(new java.util.Date(nowMillis + jwtExpirationMs))
                .signWith(key, io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();
    }
    //validate jwt token
    //extract username from jwt token
}
