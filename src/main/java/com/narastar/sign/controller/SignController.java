package com.narastar.sign.controller;

import com.narastar.config.JwtUtil;
import com.narastar.sign.service.SignService;
import com.narastar.sign.vo.AuthCodesVO;
import com.narastar.sign.vo.Members;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final SignService signService;
    private final JwtUtil jwtUtil;

    /***
     * 회원가입 인증번호 생성
     * @param paramMap
     * @return
     */
    @PostMapping("/selectCertifiNumber")
    public ResponseEntity<Map<String,Object>> requestCertification(@RequestBody Map<String,Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();

        try{
            AuthCodesVO authCodesVo = signService.selectCertification(paramMap);

            Random random = new Random();
            String code = (100000 + random.nextInt(900000))+"";  // 100000 ~ 999999 범위

            paramMap.put("code", code);

            //인증번호 인증시간 유효할 경우
            if(authCodesVo != null){
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                LocalDateTime createAt = LocalDateTime.parse(authCodesVo.getCreateAt(), dateTimeFormatter);
                LocalDateTime expiresAt = LocalDateTime.parse(authCodesVo.getExpiresAt(), dateTimeFormatter);

                LocalDateTime now = LocalDateTime.now();

                if(!now.isBefore(createAt) && !now.isAfter(expiresAt)){
                    paramMap.put("codesId", authCodesVo.getCodesId());
                    signService.updateCertifiDate(paramMap);
                }else{
                    signService.requestCertification(paramMap);
                }

            }else{
                signService.requestCertification(paramMap);
            }

            resultMap.put("successAt", "200");
        }catch(RuntimeException e){
            resultMap.put("successAt", "100");
            resultMap.put("message", "인증번호 발송 중 에러가 발생하였습니다.\n관리자에게 문의 바랍니다.");
        }

        return ResponseEntity.ok(resultMap);
    }

    /***
     * 인증번호 검증
     * @param paramMap
     * @return
     */
    @PostMapping("/certifiNumber")
    public ResponseEntity<Map<String,Object>> certifiNumber(@RequestBody Map<String,Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();

        try{
            AuthCodesVO authCodesVo = signService.selectCertification(paramMap);

            if(authCodesVo != null){
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                LocalDateTime createAt = LocalDateTime.parse(authCodesVo.getCreateAt(), dateTimeFormatter);
                LocalDateTime expiresAt = LocalDateTime.parse(authCodesVo.getExpiresAt(), dateTimeFormatter);

                LocalDateTime now = LocalDateTime.now();

                if(!now.isBefore(createAt) && !now.isAfter(expiresAt)){
                    if(authCodesVo.getCode().equals(paramMap.get("certifiNumber"))){
                        paramMap.put("codesId", authCodesVo.getCodesId());
                        signService.updateCertifiVerified(paramMap);
                        resultMap.put("successAt", "200");
                    }else{
                        resultMap.put("successAt", "150");
                        resultMap.put("message", "인증번호가 존재하지 않습니다.");
                    }
                }else{
                    resultMap.put("successAt", "150");
                    resultMap.put("message", "인증번호가 존재하지 않습니다.");
                }
            }

        }catch(RuntimeException e){
            resultMap.put("successAt", "100");
            resultMap.put("message", "인증번호 발송 중 에러가 발생하였습니다.\n관리자에게 문의 바랍니다.");
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
        Map<String, Object> resultMap = new HashMap<>();

        int count = signService.dupleNickName(paramMap);

        if(count > 0){
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

        String rawPassword = paramMap.get("password").toString();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        paramMap.put("encodedPassword", encodedPassword);

        signService.insertMembers(paramMap);

        resultMap.put("successAt", "200");
        return ResponseEntity.ok(resultMap);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,Object> paramMap, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<>();

        Members members = signService.selectLogin(paramMap);

        if(members == null){
            resultMap.put("successAt", "100");
            resultMap.put("message", "존재하지 않는 회원입니다.");
        }else{
            if (!passwordEncoder.matches(paramMap.get("loginPw").toString(), members.getPassword())) {
                resultMap.put("successAt", "100");
                resultMap.put("message", "존재하지 않는 회원입니다.");
            }else{
                String token = jwtUtil.generateToken(paramMap.get("phoneInput").toString());

                jwtUtil.sendTokenInCookie(token, response);

                resultMap.put("successAt", "200");
            }
        }

        return ResponseEntity.ok(resultMap);
    }
}
