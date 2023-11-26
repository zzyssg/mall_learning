package com.zzy.malllearningmybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MallLearningMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallLearningMybatisApplication.class, args);
    }

}
