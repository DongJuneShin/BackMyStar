package com.narastar.common.mapper;

import com.narastar.common.vo.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonMapper {
    List<Menu> selectMenus();
}
