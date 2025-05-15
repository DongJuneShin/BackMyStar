package com.narastar.common.controller;

import com.narastar.common.service.CommonService;
import com.narastar.common.vo.Menu;
import com.narastar.config.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common")
public class CommonController {
    private final JwtUtil jwtUtil;
    private final CommonService commonService;

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

    /***
     * 메뉴 불러오기
     * @param request
     * @return
     */
    @GetMapping("/selectMenus")
    public ResponseEntity<?> selectMenus(HttpServletRequest request){
        Map<String, Object> returnMap = new HashMap<>();

        try{
            List<Menu> menuList = commonService.selectMenus();
            returnMap.put("successAt", "200");
            returnMap.put("menus", menuList);
        }catch(Exception e){
            returnMap.put("successAt", "100");
            returnMap.put("message", "메뉴 불러오기 중 오류가 발생하였습니다.\n관리자에게 문의 바랍니다.");
        }

        return ResponseEntity.ok(returnMap);
    }
}
