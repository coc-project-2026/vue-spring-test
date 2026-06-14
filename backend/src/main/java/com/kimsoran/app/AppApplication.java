package com.kimsoran.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * kimsoran 애플리케이션 진입점.
 * 신입 메모: main 메서드가 실행되면 내장 Tomcat과 Spring Context가 함께 기동됩니다.
 */
@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
