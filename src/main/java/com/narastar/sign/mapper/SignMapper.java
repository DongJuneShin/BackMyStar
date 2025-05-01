package com.narastar.sign.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface SignMapper {
    int selectLogin(Map<String, Object> paramMap);
}
