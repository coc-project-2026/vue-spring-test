# kimsoran 아키텍처 설계

> 신입 학습용 심플 온보딩 앱. 복잡한 추상화 금지, 직관적 구조 우선.

## 1. 시스템 개요

- **백엔드**: Spring Boot 3.x (Java 21) + PostgreSQL 15
- **프론트엔드**: Vue 3 + Vite + Pinia + Vue Router
- **인증**: JWT (HS256, Access Token 단일 토큰 / 신입 학습 목적으로 Refresh Token 생략)
- **API 스타일**: REST
- **배포**: Docker

## 2. 컴포넌트 구성

```
[Vue SPA] --(fetch + Authorization: Bearer)--> [Spring Boot REST API] --> [PostgreSQL]
                                                      |
                                                 [JwtFilter]
```

## 3. 백엔드 레이어 (단순 3-tier)

```
Controller  -->  Service  -->  Repository  -->  DB
    ^
    |
JwtAuthenticationFilter (SecurityFilterChain)
```

- **Controller**: HTTP 입출력
- **Service**: 비즈니스 로직 (로그인, 토큰 발급)
- **Repository**: JPA 인터페이스
- 신입이 추적하기 쉽도록 복잡한 인터페이스/어댑터 계층 미도입

## 4. 패키지 구조 (backend)

```
com.kimsoran.app
 ├─ AppApplication.java          // 엔트리포인트
 ├─ auth/
 │   ├─ AuthController.java      // POST /api/auth/login
 │   ├─ AuthService.java         // 이메일/비번 검증, JWT 발급
 │   ├─ LoginRequest.java
 │   └─ LoginResponse.java
 ├─ user/
 │   ├─ User.java                // JPA Entity
 │   └─ UserRepository.java
 ├─ security/
 │   ├─ JwtTokenProvider.java    // JWT 생성/검증
 │   ├─ JwtAuthenticationFilter.java
 │   └─ SecurityConfig.java
 └─ common/
     └─ GlobalExceptionHandler.java
```

## 5. 프론트엔드 구조

```
frontend/src
 ├─ main.js
 ├─ App.vue
 ├─ router/index.js              // /login, /menu, 메뉴 stub 3개
 ├─ stores/auth.js               // Pinia: JWT 저장/로그아웃
 ├─ api/client.js                // fetch wrapper + 토큰 주입
 ├─ views/
 │   ├─ LoginView.vue
 │   ├─ MenuView.vue             // 메뉴 A/B/C 버튼
 │   └─ stubs/
 │       ├─ MyPageView.vue
 │       ├─ StudyView.vue
 │       └─ MenuCStub.vue
```

- 라우팅: 메뉴 A/B/C는 stub 페이지로 "TODO: 신입이 직접 구현" 안내만 노출
- 인증 가드: `beforeEach`에서 토큰 없으면 `/login` 리다이렉트

## 6. 보안 정책

- 비밀번호: BCrypt 해시 저장
- JWT: HS256, Secret은 `application.yml` + 환경변수(`JWT_SECRET`)
- 만료: 1시간 (3600s) — 학습용 단일 Access Token
- CORS: `http://localhost:5173` (Vite) 허용
- 토큰 저장 위치: **localStorage** (학습용 단순화, 운영 권장은 httpOnly 쿠키 — README에 명시)

## 7. DB 스키마

```sql
CREATE TABLE users (
  id         BIGSERIAL PRIMARY KEY,
  email      VARCHAR(255) UNIQUE NOT NULL,
  password   VARCHAR(255) NOT NULL,  -- BCrypt 해시
  created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
```

초기 시드: `test@kimsoran.dev / password123!`

## 8. 신입 학습 포인트

1. **Controller → Service → Repository 흐름** 따라가 보기
2. **JwtAuthenticationFilter**가 어떻게 요청을 가로채는지 디버깅
3. **Vue Router beforeEach**에서 인증 가드 로직 확인
4. 메뉴 A/B/C stub 페이지에 실제 라우팅/화면 직접 구현
