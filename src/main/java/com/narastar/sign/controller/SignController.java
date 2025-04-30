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

    /***
     * 회원가입 인증번호 생성
     * @param paramMap
     * @return
     */
    @PostMapping("/selectCertifiNumber")
    public ResponseEntity<Map<String,Object>> requestCertification(@RequestBody Map<String,Object> paramMap) {
        System.err.println("paramMap : "+paramMap);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("successAt", "200");
        return ResponseEntity.ok(resultMap);
    }

    /***
     * 인증번호 검증
     * @param paramMap
     * @return
     */
    @PostMapping("/certifiNumber")
    public ResponseEntity<Map<String,Object>> certifiNumber(@RequestBody Map<String,Object> paramMap) {
        System.err.println("인증번호 맵 : "+paramMap);
        String certifiNumber = paramMap.get("certifiNumber").toString();

        Map<String, Object> resultMap = new HashMap<>();

        if(certifiNumber.equals("123456")){
            resultMap.put("successAt", "200");      //성공
        }else{
            resultMap.put("successAt", "100");      //실패
        }

        return ResponseEntity.ok(resultMap);
    }

    /***
     * 닉네임 중복검사
     * @param paramMap
     * @return
     */
    @PostMapping("/dupleNickName")
    public ResponseEntity<Map<String,Object>> dupleNickName(@RequestBody Map<String,Object> paramMap) {
        String nickName = paramMap.get("nickname").toString();
        Map<String, Object> resultMap = new HashMap<>();

        if(nickName.equals("나둥이")){
            resultMap.put("successAt", "100");
        }else{
            resultMap.put("successAt", "200");
        }
        return ResponseEntity.ok(resultMap);
    }

    /***
     * 회원가입
     * @param paramMap
     * @return
     */
    @PostMapping("/signUp")
    public ResponseEntity<Map<String,Object>> signUp(@RequestBody Map<String,Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("successAt", "200");
        return ResponseEntity.ok(resultMap);
    }
}
