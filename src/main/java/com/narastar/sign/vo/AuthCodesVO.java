package com.narastar.sign.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AuthCodesVO {
    private int codesId;            //인증코드기본키
    private String phoneNumber;     //핸드폰번호
    private String code;            //인증코드
    private String expiresAt;         //인증코드 만료 시간
    private int isVerifed;          //인증성공여부
    private String createAt;          //생성시간
}
