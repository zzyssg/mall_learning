package com.zzy.malladmin.component;

import cn.hutool.core.util.URLUtil;
import com.zzy.malladmin.service.DynamicSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @ClassName DynamicSecurityMetadataSource
 * @Author ZZy
 * @Date 2023/10/22 15:00
 * @Description
 * @Version 1.0
 */
@Component
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private Map<String,ConfigAttribute> configAttributeMap = null;

    @Autowired
    private DynamicSecurityService dynamicSecurityService;

    @PostConstruct
    private void loadDataSource() {
        this.configAttributeMap = dynamicSecurityService.loadDataSource();
    }

    public void clearDataSource() {
        this.configAttributeMap.clear();
        this.configAttributeMap = null;
    }

    /**
     * 自己实现访问接口所需要的权限（资源 功能、pattern）
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (configAttributeMap == null) {
            this.loadDataSource();
        }
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        //获取当前访问路径
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        String path = URLUtil.getPath(requestUrl);
        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = configAttributeMap.keySet().iterator();
        //获取访问该路径所需资源
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            if (pathMatcher.match(pattern, path)) {
                configAttributes.add(configAttributeMap.get(pattern));
            }
        }

        //未设置操作请求权限，返回空集合
        return configAttributes;
    }



    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
