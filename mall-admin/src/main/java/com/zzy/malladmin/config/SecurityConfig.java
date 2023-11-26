package com.zzy.malladmin.config;

import com.zzy.malladmin.common.IgnoreUrlsConfig;
import com.zzy.malladmin.component.*;
import com.zzy.malladmin.service.DynamicSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @ClassName SecurityConfig
 * @Author ZZy
 * @Date 2023/10/17 9:36
 * @Description
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private DynamicSecurityService dynamicSecurityService;

    @Autowired
    private DynamicSecurityFilter dynamicSecurityFilter;

    @Autowired
    RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint;

    @Autowired
    IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = httpSecurity.authorizeRequests();
        //不许要保护的资源路径，允许访问
        for (String ignoreUrl : ignoreUrlsConfig.getUrls()) {
            urlRegistry.antMatchers(ignoreUrl).permitAll();
        }
        //允许跨域请求的options
        urlRegistry.antMatchers(HttpMethod.OPTIONS).permitAll();
        //任何请求需要身份验证
        urlRegistry.and()
                .authorizeRequests()
                .anyRequest()
                .fullyAuthenticated()
                //关闭跨站请求访问 and 不适用session
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //自定义权限处理类
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restfulAuthenticationEntryPoint)
                //自定义权限拦截器 JWT 过滤器
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);



        //有动态权限控制时，添加权限过滤器 TODO
        if (dynamicSecurityService != null) {
            urlRegistry.and()
                    .addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);
        }

        return httpSecurity.build();
    }


}
