package com.narastar.sign.serviceImpl;

import com.narastar.sign.mapper.SignMapper;
import com.narastar.sign.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {
    private final SignMapper signMapper;

    @Override
    public int selectLogin(Map<String, Object> paramMap) {

        return signMapper.selectLogin(paramMap);
    }
}
