package com.narastar.schdule.serviceImpl;

import com.narastar.schdule.mapper.SchduleMapper;
import com.narastar.schdule.service.SchduleService;
import com.narastar.schdule.vo.SchduleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SchduleServiceImpl implements SchduleService {

    @Autowired
    private final SchduleMapper schduleMapper;
    @Override
    public void insertSchdule(SchduleVO schduleVO) {
        schduleMapper.insertSchdule(schduleVO);
    }

    @Override
    public List<SchduleVO> selectScheduleList(Map<String, Object> paramMap) {
        return schduleMapper.selectScheduleList(paramMap);
    }

    @Override
    public void deleteSchedule(Map<String, Object> paramMap) {
        schduleMapper.deleteSchedule(paramMap);
    }
}
