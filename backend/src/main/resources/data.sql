-- 신입 학습용 기본 계정 시드
-- 이메일: test@kimsoran.dev / 비밀번호: password123!
-- 아래 해시는 BCrypt("password123!") 결과물 (strength 10)
INSERT INTO users (email, password, created_at)
SELECT 'test@kimsoran.dev',
       '$2a$10$D9Gk0uJ4Nq2.cYj4k7y6/.3oE9vU5zZ6Zz7QG0E2wO4t4k2G3xH6y',
       CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'test@kimsoran.dev');
