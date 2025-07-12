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

    @PostMapping("/selectScheduleList")
    public ResponseEntity<?> selectScheduleList(
            HttpServletRequest request,
            @RequestBody Map<String, String> paraMap) {

        Map<String, Object> returnMap = new HashMap<>();

        //LocalDate start = LocalDate.parse(startDate);
        //LocalDate end = LocalDate.parse(endDate);

        String token = jwtFilter.extractTokenFromCookie(request);
        Claims claims = jwtUtil.getClaimsFromToken(token);
        String phoneNumber = claims.get("sub", String.class);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startDate", paraMap.get("startDate"));
        paramMap.put("endDate", paraMap.get("endDate"));
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
