package com.narastar.common;

import com.narastar.config.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common")
public class Common {
    private final JwtUtil jwtUtil;

    /***
     * 토큰이 발급되었는지 안되었는지
     * 확인
     * @param request
     * @return
     */
    @GetMapping("/check")
    public ResponseEntity<?> checkLoginStatus(HttpServletRequest request) {
        String token = jwtUtil.resolveTokenFromCookie(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        return ResponseEntity.ok("Authorized");
    }
}
