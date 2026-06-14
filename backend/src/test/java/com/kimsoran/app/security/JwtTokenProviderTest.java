package com.kimsoran.app.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JwtTokenProvider 단위 테스트.
 */
class JwtTokenProviderTest {

    private final JwtTokenProvider provider =
            new JwtTokenProvider("kimsoran-learning-secret-please-change-in-production-32bytes!!", 3600);

    @Test
    void 생성한_토큰을_검증하면_subject가_이메일이다() {
        String token = provider.createToken(1L, "test@kimsoran.dev");
        String email = provider.validateAndGetEmail(token);
        assertEquals("test@kimsoran.dev", email);
    }

    @Test
    void 잘못된_토큰은_예외() {
        assertThrows(Exception.class, () -> provider.validateAndGetEmail("not.a.token"));
    }
}
