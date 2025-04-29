package com.narastar.config;  // 패키지 선언

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 대해 CORS 허용 (출처는 Vue 개발 서버의 포트로 설정)
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")  // Vue.js 개발 서버 포트
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}