package com.narastar.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    /**
     * 환경변수에 없으니 시스템 변수에 삽입
     */
    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    /***
     * JWT 생성
     * @param username
     * @return
     */
    public String generateToken(String userId, String userName) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("nickname", userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // 만료시간 1시간
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }


    /***
     * JWT에서 사용자 이름 추출
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()  // parser() 대신 parserBuilder() 사용
                .setSigningKey(secretKey.getBytes())  // 비밀 키를 바이트 배열로 전달
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /***
     * JWT 유효성 검사
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /***
     * JWT를 쿠키로 설정
     * @param jwt
     * @param response
     */
    public void sendTokenInCookie(String jwt, HttpServletResponse response) {
        Cookie cookie = new Cookie("token", jwt);
        cookie.setHttpOnly(false);             // JavaScript에서 접근 불가
        cookie.setSecure(true);               // HTTPS 환경에서만 전달
        cookie.setPath("/");                  // 전체 경로에서 유효
        cookie.setMaxAge(60 * 60);            // 1시간 (초 단위)
        cookie.setDomain("localhost");        // 개발 시엔 localhost (배포 시 도메인 설정)
        cookie.setAttribute("SameSite", "None"); // CORS 대응을 위해 필요

        response.addCookie(cookie);
    }

    /***
     * 토큰을 쿠키에 삽입
     * @param request
     * @return
     */
    public String resolveTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
