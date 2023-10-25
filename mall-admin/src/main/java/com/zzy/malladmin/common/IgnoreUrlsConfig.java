package com.zzy.malladmin.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SecurityIgnore
 * @Author ZZy
 * @Date 2023/10/17 10:46
 * @Description
 * @Version 1.0
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "security.ignored")
public class IgnoreUrlsConfig {
    List<String> urls = new ArrayList<>();
}
