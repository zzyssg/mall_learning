package com.zzy.malladmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @ClassName GlobalCorsConfig
 * @Author ZZy
 * @Date 2023/10/16 22:55
 * @Description
 * @Version 1.0
 */
@Configuration
public class GlobalCorsConfig {

    /**
     * 跨域用的过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许所有域名进行跨域访问
        corsConfiguration.addAllowedOriginPattern("*");
        //允许跨越发送cookie
        corsConfiguration.setAllowCredentials(true);
        //放行全部请求头信息
        corsConfiguration.addAllowedHeader("*");
        //允许所有方法跨域调用
        corsConfiguration.addAllowedMethod("*");

        //对接口配置跨域设置
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);

    }



}
