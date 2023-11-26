package com.zzy.malladmin.config;

import cn.hutool.core.collection.CollUtil;
import com.zzy.malladmin.mbg.model.UmsAdmin;
import com.zzy.malladmin.mbg.model.UmsResource;
import com.zzy.malladmin.service.DynamicSecurityService;
import com.zzy.malladmin.service.UmsAdminService;
import com.zzy.malladmin.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MallSecurityConfig
 * @Author ZZy
 * @Date 2023/10/19 22:57
 * @Description
 * @Version 1.0
 */
@Configuration
public class MallSecurityConfig {
    @Autowired
    private UmsAdminService adminService;

    @Autowired
    private UmsResourceService umsResourceService;

    //TODO 为什么这里返回UserDetailsService,而不是UserDetails
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> adminService.loadUserDetailByUsername(username);

    }


    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new HashMap<>();
                List<UmsResource> umsResourceList = umsResourceService.listAll();
                for (UmsResource umsResource : umsResourceList) {
                    map.put(umsResource.getUrl(), new SecurityConfig(umsResource.getId() + ":" + umsResource.getName()));
                }
                return map;
            }
        };
    }






}


