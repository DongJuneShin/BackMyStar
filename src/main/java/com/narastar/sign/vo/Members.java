package com.narastar.sign.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Members {
    private String phoneNumber;         //핸드폰번호
    private String nickname;            //닉네임
    private String password;            //비밀번호
    private String createAt;            //생성시간
    private String memberGrade;         //회원등급
}
