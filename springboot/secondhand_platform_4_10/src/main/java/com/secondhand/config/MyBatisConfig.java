package com.secondhand.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.secondhand.mapper")
public class MyBatisConfig {
}