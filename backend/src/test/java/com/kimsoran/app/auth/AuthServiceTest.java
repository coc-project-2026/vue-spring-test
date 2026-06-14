package com.kimsoran.app.auth;

import com.kimsoran.app.security.JwtTokenProvider;
import com.kimsoran.app.user.User;
import com.kimsoran.app.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AuthService 단위 테스트.
 * 신입 메모: Mockito로 의존 객체를 가짜(mock)로 대체해 Service 로직만 검증합니다.
 */
class AuthServiceTest {

    @Test
    void 로그인_성공시_토큰과_만료초를_반환한다() {
        // given
        UserRepository userRepo = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        JwtTokenProvider jwt = mock(JwtTokenProvider.class);

        User user = new User("test@kimsoran.dev", "hashed");
        when(userRepo.findByEmail("test@kimsoran.dev")).thenReturn(Optional.of(user));
        when(encoder.matches("password123!", "hashed")).thenReturn(true);
        when(jwt.createToken(isNull(), eq("test@kimsoran.dev"))).thenReturn("jwt.token.here");
        when(jwt.getExpiresInSeconds()).thenReturn(3600L);

        AuthService service = new AuthService(userRepo, encoder, jwt);

        LoginRequest req = new LoginRequest();
        req.setEmail("test@kimsoran.dev");
        req.setPassword("password123!");

        // when
        LoginResponse res = service.login(req);

        // then
        assertEquals("jwt.token.here", res.getAccessToken());
        assertEquals("Bearer", res.getTokenType());
        assertEquals(3600L, res.getExpiresIn());
    }

    @Test
    void 이메일_미존재시_InvalidCredentials() {
        UserRepository userRepo = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        JwtTokenProvider jwt = mock(JwtTokenProvider.class);
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());

        AuthService service = new AuthService(userRepo, encoder, jwt);

        LoginRequest req = new LoginRequest();
        req.setEmail("nope@kimsoran.dev");
        req.setPassword("password123!");

        assertThrows(AuthService.InvalidCredentialsException.class, () -> service.login(req));
    }

    @Test
    void 비밀번호_불일치시_InvalidCredentials() {
        UserRepository userRepo = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        JwtTokenProvider jwt = mock(JwtTokenProvider.class);
        User user = new User("test@kimsoran.dev", "hashed");
        when(userRepo.findByEmail("test@kimsoran.dev")).thenReturn(Optional.of(user));
        when(encoder.matches("wrong", "hashed")).thenReturn(false);

        AuthService service = new AuthService(userRepo, encoder, jwt);

        LoginRequest req = new LoginRequest();
        req.setEmail("test@kimsoran.dev");
        req.setPassword("wrong");

        assertThrows(AuthService.InvalidCredentialsException.class, () -> service.login(req));
    }
}
