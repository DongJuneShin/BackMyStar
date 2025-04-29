package com.narastar.sign.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sign")
public class SignController {

    @PostMapping("/certifiNumber")
    public ResponseEntity<Map<String,Object>> requestCertification(@RequestBody Map<String,Object> paramMap) {
        System.err.println("paramMap : "+paramMap);
        String telecom = (String) paramMap.get("telecom");
        String phoneNumber = (String) paramMap.get("phoneNumber");
        System.err.println("telecom : "+telecom);
        System.err.println("phoneNumber : "+phoneNumber);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("successAt", "200");
        // 인증 요청 처리 로직
        return ResponseEntity.ok(resultMap);
    }
}
