package com.zzy.boot_bootis.component;

import cn.hutool.json.JSONUtil;
import com.zzy.boot_bootis.common.api.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName RestfulAccessDeniedHandler
 * @Author ZZy
 * @Date 2023/9/7 11:12
 * @Description
 * @Version 1.0
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //设置content-type（定义网络文件的类型和网页编码，让文件接收方决定接受形式和解码方式）响应头，使得客户端浏览器使用content-type解读响应数据
        //未指定，默认content-type为TEXT/HTML
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        //响应内容为字符
        //向浏览器内写入内容
        response.getWriter().println(JSONUtil.parse(CommonResult.forbidden(accessDeniedException.getMessage())));
        //缓冲信息刷新到页面 ？？
        response.getWriter().flush();

    }
}
