package com.zzy.boot_bootis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisConfig
 * @Author ZZy
 * @Desc 动态生成的mapper接口的路径 dao下和mbg.mapper下
 * @Date 2023/9/3 16:42
 * @Version 1.0
 */
@Configuration
@MapperScan({"com.zzy.boot_bootis.dao","com.zzy.boot_bootis.mbg.mapper"})
public class MybatisConfig {

}
