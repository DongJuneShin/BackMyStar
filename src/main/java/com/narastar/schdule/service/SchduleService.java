package com.narastar.schdule.service;

import com.narastar.schdule.vo.SchduleVO;

import java.util.List;
import java.util.Map;

public interface SchduleService {
    void insertSchdule(SchduleVO schduleVO);

    List<SchduleVO> selectScheduleList(Map<String, Object> paramMap);

    void deleteSchedule(Map<String, Object> paramMap);
}
