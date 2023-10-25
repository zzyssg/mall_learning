package com.zzy.malladmin.config;

import com.zzy.malladmin.component.DynamicAccessDecisionManager;
import com.zzy.malladmin.component.DynamicSecurityFilter;
import com.zzy.malladmin.component.DynamicSecurityMetadataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName CommonSecurityConfig
 * @Author ZZy
 * @Date 2023/10/6 16:21
 * @Description 通用Bean、Security通用Bean、动态权限通用Bean
 * @Version 1.0
 */
@Configuration
public class CommonSecurityConfig {


    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
        return new DynamicAccessDecisionManager();
    }

    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
        return new DynamicSecurityMetadataSource();
    }

    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicSecurityFilter dynamicSecurityFilter() {
        return new DynamicSecurityFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
