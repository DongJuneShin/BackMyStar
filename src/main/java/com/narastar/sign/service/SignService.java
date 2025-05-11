package com.narastar.sign.service;

import com.narastar.sign.vo.AuthCodesVO;
import com.narastar.sign.vo.Members;

import java.util.Map;

public interface SignService {
    Members selectLogin(Map<String, Object> paramMap);

    void requestCertification(Map<String, Object> paramMap);

    AuthCodesVO selectCertification(Map<String, Object> paramMap);

    void updateCertifiDate(Map<String, Object> paramMap);

    void updateCertifiVerified(Map<String, Object> paramMap);

    int dupleNickName(Map<String, Object> paramMap);

    void insertMembers(Map<String, Object> paramMap);
}
