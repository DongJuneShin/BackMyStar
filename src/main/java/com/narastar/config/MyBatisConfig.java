package com.narastar.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {
        "com.narastar.sign.mapper",
        "com.narastar.common.mapper",
        "com.narastar.schdule.mapper"
})
public class MyBatisConfig {
}
