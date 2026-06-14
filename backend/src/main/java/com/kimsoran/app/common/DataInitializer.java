package com.kimsoran.app.common;

import com.kimsoran.app.user.User;
import com.kimsoran.app.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 앱 기동 시 학습용 테스트 계정을 자동 생성합니다.
 * 신입 메모: BCrypt 해시를 직접 계산하므로 data.sql 해시 불일치 문제가 없습니다.
 * 이메일: test@kimsoran.dev / 비밀번호: password123!
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("test@kimsoran.dev").isEmpty()) {
            userRepository.save(new User("test@kimsoran.dev", passwordEncoder.encode("password123!")));
            log.info("[DataInitializer] 테스트 계정 생성 완료: test@kimsoran.dev");
        } else {
            log.info("[DataInitializer] 테스트 계정 이미 존재");
        }
    }
}
