package com.narastar.common.serviceImpl;

import com.narastar.common.mapper.CommonMapper;
import com.narastar.common.service.CommonService;
import com.narastar.common.vo.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final CommonMapper commonMapper;

    @Override
    public List<Menu> selectMenus() {
        return commonMapper.selectMenus();
    }
}
