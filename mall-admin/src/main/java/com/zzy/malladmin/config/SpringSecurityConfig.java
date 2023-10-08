package com.zzy.malladmin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName SpringSecurityConfig
 * @Author ZZy
 * @Date 2023/10/6 19:45
 * @Description
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected  void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
//                .antMatchers("/doc.html")
//                .permitAll()
//                .antMatchers("/swagger-ui.html")
//                .permitAll()
//                .antMatchers("/webjars/**")
//                .permitAll()
//                .antMatchers("/swagger-resources/**")
//                .permitAll()
//                .antMatchers("/api-docs/**")
//                .permitAll()
//                .antMatchers("/v3/**")
//                .permitAll()
//                .antMatchers("/csrf")
//                .permitAll()
//                .antMatchers("/")
//                .permitAll()
                .anyRequest()
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable();
    }
}
