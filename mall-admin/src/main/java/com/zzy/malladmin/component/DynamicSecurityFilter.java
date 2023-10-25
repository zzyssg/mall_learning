package com.zzy.malladmin.component;

import com.zzy.malladmin.common.IgnoreUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * @ClassName DynamicSecurityFilter
 * @Author ZZy
 * @Date 2023/10/22 14:56
 * @Description
 * @Version 1.0
 */
@Component
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{

    }

    @Autowired
    public void setMyAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        //新建FilterInvocation
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);

        //放行options请求
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            //不是直接return，需要正常执行fi.dofilter
//            return;
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        }

        //放行白名单
        AntPathMatcher matcher = new AntPathMatcher();
        for (String path : ignoreUrlsConfig.getUrls()) {
            if (matcher.match(path, request.getRequestURI())) {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                return;
            }
        }
        //调用decide进行鉴权
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //attention
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetadataSource;
    }
}
