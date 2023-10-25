package com.zzy.malladmin.component;

import cn.hutool.core.collection.CollUtil;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * @ClassName DynamicAccessDecisionManager
 * @Author ZZy
 * @Date 2023/10/22 15:06
 * @Description   权限校验 TODO  configuration 是配置好（在哪里配置的？）的访问【当前接口】所需要的权限
 * @Version 1.0
 */
@Component
public class DynamicAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication,
                       Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //接口未被配置资源时，直接放行
        if (CollUtil.isEmpty(configAttributes)) {
            return;
        }
        //需要资源和拥有资源比对
        Iterator<ConfigAttribute> attributeIterator = configAttributes.iterator();
        while (attributeIterator.hasNext()) {
            ConfigAttribute attribute = attributeIterator.next();
            //将访问所需资源和用户拥有的资源做个对比
            String neededAuthority = attribute.getAttribute();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (neededAuthority.trim().equals(authority.getAuthority())) {
                    return;
                }
            }

        }
        throw new AccessDeniedException("您没有权限访问此接口");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
