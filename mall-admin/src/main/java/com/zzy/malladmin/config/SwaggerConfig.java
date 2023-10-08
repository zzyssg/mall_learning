package com.zzy.malladmin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @ClassName SwaggerConfig
 * @Author ZZy
 * @Date 2023/9/27 15:36
 * @Description
 * @Version 1.0
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig extends WebMvcConfigurationSupport {


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zzy.malladmin.controller")) //设置swagger扫描路径
                .build();
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //访问静态资源
        registry.addResourceHandler("swagger-ui", "doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }


}
