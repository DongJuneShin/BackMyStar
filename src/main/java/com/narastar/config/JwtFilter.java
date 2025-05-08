package com.narastar.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        // 로그인과 일부 URL은 인증 없이 접근 가능하므로 처리
        if (uri.equals("/sign/login") || uri.equals("/sign/selectCertifiNumber") || uri.equals("/sign/signUp") || uri.equals("/sign/dupleNickName") || uri.equals("/sign/certifiNumber")) {
            filterChain.doFilter(request, response);  // JWT 필터 건너뛰기
            return;
        }

        // JWT 토큰 추출
        String token = request.getHeader("Authorization");

        // 토큰이 없거나 유효하지 않으면 401 Unauthorized 응답
        if (token == null || !token.startsWith("Bearer ") || !jwtUtil.validateToken(token.substring(7))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401 Unauthorized 응답
            response.getWriter().write("Unauthorized access. Please log in.");
            return;
        }

        // 유효한 토큰이라면, 인증된 사용자로 설정
        String username = jwtUtil.getUsernameFromToken(token.substring(7));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));

        filterChain.doFilter(request, response);
    }
}
