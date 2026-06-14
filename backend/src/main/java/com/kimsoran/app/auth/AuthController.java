package com.kimsoran.app.auth;

import com.kimsoran.app.user.User;
import com.kimsoran.app.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 인증 관련 엔드포인트.
 * 신입 메모: @RestController는 응답을 JSON으로 자동 직렬화합니다.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    /** POST /api/auth/login — 이메일/비번 로그인 */
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /** GET /api/me — 현재 로그인한 사용자 정보 (토큰 검증 확인용) */
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthService.InvalidCredentialsException());
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "email", user.getEmail()
        ));
    }
}
