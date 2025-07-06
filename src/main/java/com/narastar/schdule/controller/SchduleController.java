package com.narastar.schdule.controller;

import com.narastar.common.vo.Menu;
import com.narastar.config.JwtFilter;
import com.narastar.config.JwtUtil;
import com.narastar.schdule.service.SchduleService;
import com.narastar.schdule.vo.SchduleVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class SchduleController {

    @Autowired
    private final SchduleService schduleService;
    private final JwtUtil jwtUtil;
    private final JwtFilter jwtFilter;

    @PostMapping("/insertSchdule")
    public ResponseEntity<?> insertSchdule(HttpServletRequest request, @RequestBody SchduleVO schduleVO){
        Map<String, Object> returnMap = new HashMap<>();

        try{
            schduleService.insertSchdule(schduleVO);
            returnMap.put("successAt", "200");
        }catch(Exception e){
            returnMap.put("successAt", "100");
            returnMap.put("message", "일정 저장 중 문제가 발생하였습니다.");
        }

        return ResponseEntity.ok(returnMap);
    }

    @GetMapping("/selectScheduleList")
    public ResponseEntity<?> selectScheduleList(HttpServletRequest request, @RequestParam String yearMonth){
        Map<String, Object> returnMap = new HashMap<>();
        YearMonth baseMonth = YearMonth.parse(yearMonth);

        // 시작일: 해당 연월의 1일
        LocalDate startDate = baseMonth.atDay(1); // 2025-06-01

        // 종료일: 두 달 뒤의 마지막 날
        LocalDate endDate = baseMonth.plusMonths(2).atEndOfMonth(); // 2025-08-31

        String token = jwtFilter.extractTokenFromCookie(request); // 쿠키에서 JWT 추출
        Claims claims = jwtUtil.getClaimsFromToken(token); // JWT 파싱
        String phoneNumber = claims.get("sub", String.class);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("phoneNumber", phoneNumber);

        List<SchduleVO> schduleList = schduleService.selectScheduleList(paramMap);

        returnMap.put("successAt", "200");
        returnMap.put("data", schduleList);

        return ResponseEntity.ok(returnMap);
    }

    @PostMapping("/deleteSchedule")
    public ResponseEntity<?> deleteSchedule(HttpServletRequest request,@RequestBody Map<String, Object> paramMap){
        Map<String, Object> returnMap = new HashMap<>();

        try{
            schduleService.deleteSchedule(paramMap);
            returnMap.put("successAt", "200");
        }catch(Exception e){
            returnMap.put("successAt", "100");
        }

        return ResponseEntity.ok(returnMap);
    }
}
