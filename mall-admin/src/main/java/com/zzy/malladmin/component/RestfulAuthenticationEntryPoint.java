package com.zzy.malladmin.component;

import cn.hutool.json.JSONUtil;
import com.zzy.malladmin.common.CommonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName RestfulAuthenticationEntryPointHandler
 * @Author ZZy
 * @Date 2023/10/17 9:59
 * @Description 自定义未登录或者token失效时的返回结果
 * @Version 1.0
 */
@Component
public class RestfulAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Cache-Control","no-cache");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(JSONUtil.parse(CommonResult.unauthorized(authException.getMessage())));
        response.getWriter().flush();
    }
}
