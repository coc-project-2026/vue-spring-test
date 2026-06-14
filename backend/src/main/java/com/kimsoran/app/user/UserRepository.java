package com.kimsoran.app.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * 사용자 Repository.
 * 신입 메모: JpaRepository를 상속받으면 save/findById 등이 자동 제공됩니다.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
