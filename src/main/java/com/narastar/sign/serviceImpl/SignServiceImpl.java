package com.narastar.sign.serviceImpl;

import com.narastar.sign.mapper.SignMapper;
import com.narastar.sign.service.SignService;
import com.narastar.sign.vo.AuthCodesVO;
import com.narastar.sign.vo.Members;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {
    private final SignMapper signMapper;

    @Override
    public Members selectLogin(Map<String, Object> paramMap) {
        return signMapper.selectLogin(paramMap);
    }

    @Override
    public void requestCertification(Map<String, Object> paramMap) {
        signMapper.requestCertification(paramMap);
    }

    @Override
    public AuthCodesVO selectCertification(Map<String, Object> paramMap) {
        return signMapper.selectCertification(paramMap);
    }

    @Override
    public void updateCertifiDate(Map<String, Object> paramMap) {
        signMapper.updateCertifiDate(paramMap);
    }

    @Override
    public void updateCertifiVerified(Map<String, Object> paramMap) {
        signMapper.updateCertifiVerified(paramMap);
    }

    @Override
    public int dupleNickName(Map<String, Object> paramMap) {
        return signMapper.dupleNickName(paramMap);
    }

    @Override
    public void insertMembers(Map<String, Object> paramMap) {
        signMapper.insertMembers(paramMap);
    }

}
