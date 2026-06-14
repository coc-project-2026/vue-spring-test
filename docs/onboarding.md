# 신입 온보딩 가이드

## 1. 빠르게 실행해 보기

### 백엔드 (Spring Boot)
```bash
./gradlew bootRun
# → http://localhost:8080 기동. 기본 H2 in-memory DB + 기본 계정 자동 시드
```

### 프론트엔드 (Vue 3 + Vite)
```bash
cd frontend
npm install
npm run dev
# → http://localhost:5173 접속
```

### 테스트 계정
- email: `test@kimsoran.dev`
- password: `password123!`

## 2. 코드 읽는 순서 (추천)

1. `docs/architecture.md` — 전체 그림
2. `specs/openapi.yaml` — API 계약
3. **Backend**
   1. `AuthController.java` → 엔드포인트 확인
   2. `AuthService.java` → 비즈니스 로직
   3. `JwtTokenProvider.java` → JWT 발급/검증
   4. `JwtAuthenticationFilter.java` → 요청 가로채기
   5. `SecurityConfig.java` → 어떤 경로가 보호되는지
4. **Frontend**
   1. `src/router/index.js` → 라우팅 & 가드
   2. `src/stores/auth.js` → 토큰 저장 방식
   3. `src/api/client.js` → 헤더 주입 방식
   4. `src/views/LoginView.vue` → 제출 흐름
   5. `src/views/MenuView.vue` → 메뉴 구조

## 3. 연습 과제

### 과제 1: 메뉴 A/B/C 실제 화면 구현
- 현재 `src/views/stubs/*.vue` 는 placeholder입니다.
- 각각을 실제 기능 화면으로 교체해 보세요.
- 예) 메뉴 A = 프로필 수정, 메뉴 B = 게시글 목록 등

### 과제 2: 회원가입 추가
- 백엔드: `POST /api/auth/signup` 엔드포인트 추가
- 프론트: `/signup` 화면 + 라우터 등록

### 과제 3: 토큰 만료 처리
- `api/client.js` 에서 401 응답이면 자동으로 로그아웃 처리

### 과제 4: 운영 보안 개선
- 토큰 저장을 localStorage → httpOnly 쿠키로 이전
- Refresh Token 도입 (현재는 학습 단순화를 위해 생략)

## 4. 자주 쓰는 명령

```bash
# 백엔드 테스트
./gradlew test

# 백엔드 패키징
./gradlew bootJar

# Docker 일괄 실행 (DB 포함)
docker compose up --build
```

## 5. 문제 해결

- **CORS 에러**: Vite 프록시(/api → 8080)를 사용하므로 정상이라면 발생하지 않습니다. `vite.config.js` 의 proxy 확인.
- **로그인 실패**: 기본 계정은 `test@kimsoran.dev / password123!`. H2 콘솔은 `/h2-console` 에서 확인.
- **JWT 에러**: `JWT_SECRET` 환경변수 길이(32바이트 이상) 확인.
