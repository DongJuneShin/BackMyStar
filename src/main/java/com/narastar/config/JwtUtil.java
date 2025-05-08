package com.narastar.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
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

}
