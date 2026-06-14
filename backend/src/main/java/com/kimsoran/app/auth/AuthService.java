package com.kimsoran.app.auth;

import com.kimsoran.app.security.JwtTokenProvider;
import com.kimsoran.app.user.User;
import com.kimsoran.app.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 로그인 비즈니스 로직.
 * 신입 메모: Controller는 HTTP만 신경쓰고, 실제 검증/토큰 발급은 여기서 담당합니다.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * 이메일/비밀번호를 검증하고 JWT를 발급합니다.
     * 실패 시 InvalidCredentialsException을 던집니다.
     */
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtTokenProvider.createToken(user.getId(), user.getEmail());
        return new LoginResponse(token, jwtTokenProvider.getExpiresInSeconds());
    }

    /** 로그인 실패 공용 예외. */
    public static class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException() {
            super("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
    }
}
