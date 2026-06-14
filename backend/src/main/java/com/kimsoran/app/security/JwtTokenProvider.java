package com.kimsoran.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 생성/검증 유틸.
 * 신입 메모: HS256 대칭키. secret은 최소 32바이트 이상이어야 합니다.
 */
@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long expiresInSeconds;

    public JwtTokenProvider(@Value("${app.jwt.secret}") String secret,
                            @Value("${app.jwt.expires-in-seconds}") long expiresInSeconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiresInSeconds = expiresInSeconds;
    }

    /** 사용자 id/email을 담아 토큰을 발급한다. */
    public String createToken(Long userId, String email) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiresInSeconds * 1000);

        return Jwts.builder()
                .subject(email)
                .claim("uid", userId)
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    /** 토큰을 검증하고 이메일(subject)을 반환한다. */
    public String validateAndGetEmail(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public long getExpiresInSeconds() { return expiresInSeconds; }
}
