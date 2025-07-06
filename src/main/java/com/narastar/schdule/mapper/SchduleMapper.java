package com.narastar.schdule.mapper;

import com.narastar.schdule.vo.SchduleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SchduleMapper {
    void insertSchdule(SchduleVO schduleVO);

    List<SchduleVO> selectScheduleList(Map<String, Object> paramMap);

    void deleteSchedule(Map<String, Object> paramMap);
}
