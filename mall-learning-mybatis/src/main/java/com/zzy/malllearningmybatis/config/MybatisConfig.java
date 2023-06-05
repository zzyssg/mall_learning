package com.zzy.malllearningmybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.zzy.malllearningmybatis.dao","com.zzy.malllearningmybatis.mbg.mapper"})
public class MybatisConfig {
}
