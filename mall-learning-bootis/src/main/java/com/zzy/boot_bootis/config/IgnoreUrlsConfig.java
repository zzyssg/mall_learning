package com.zzy.boot_bootis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName IgnoreUrlsConfig
 * @Author ZZy
 * @Date 2023/9/13 0:00
 * @Description
 * @Version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "secure.ignored")
@Data
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();

}
