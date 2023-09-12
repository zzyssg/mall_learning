package com.zzy.boot_bootis.config;

import com.zzy.boot_bootis.domain.AdminUserDetails;
import com.zzy.boot_bootis.mbg.model.UmsAdmin;
import com.zzy.boot_bootis.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @ClassName MallSecuretyConfig
 * @Author ZZy
 * @Date 2023/9/13 0:14
 * @Description
 * @Version 1.0
 */
@Configuration
public class MallSecurityConfig {

    @Autowired
    private UmsAdminService umsAdminService;

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> {


            AdminUserDetails admin = umsAdminService.getAdminByUsername(username);
            if (admin != null) {
                return admin;
            }
            throw new UsernameNotFoundException("用户名或者密码错误");

        };
    }


}
