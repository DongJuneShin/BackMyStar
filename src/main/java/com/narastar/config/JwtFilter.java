package com.narastar.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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

        // 쿠키에서 토큰 추출
        String token = extractTokenFromCookie(request);

        // 토큰이 없거나 유효하지 않으면 401 응답
        if (token == null || !jwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized access. Please log in.");
            return;
        }

        // 토큰에서 Claims 추출
        Claims claims = jwtUtil.getClaimsFromToken(token);

        String username = claims.getSubject(); // userId
        String nickname = claims.get("nickname", String.class); // nickname 커스텀 클레임

        // 인증 객체 생성 시 nickname을 details에 담아서 저장
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        authenticationToken.setDetails(nickname);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 다음 필터로 진행
        filterChain.doFilter(request, response);
    }


    /***
     * JWT 인증 필터에서 쿠키 토큰 꺼내기
     * @param request
     * @return
     */
    public String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
