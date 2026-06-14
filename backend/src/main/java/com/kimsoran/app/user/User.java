package com.kimsoran.app.user;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 사용자 엔티티.
 * 신입 메모: @Entity = DB 테이블 1행을 자바 객체로 매핑한 것.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    /** BCrypt 해시 저장 (평문 절대 금지). */
    @Column(nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    protected User() { /* JPA 스펙상 기본 생성자 필요 */ }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
