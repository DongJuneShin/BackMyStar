package com.narastar.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {
        "com.narastar.sign.mapper"
})
public class MyBatisConfig {
}
