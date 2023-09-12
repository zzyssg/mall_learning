package com.zzy.boot_bootis.service;

import com.zzy.boot_bootis.domain.AdminUserDetails;

/**
 * @ClassName UmsAdminService
 * @Author ZZy
 * @Date 2023/9/4 23:24
 * @Description
 * @Version 1.0
 */
public interface UmsAdminService {

    AdminUserDetails getAdminByUsername(String username);
    String login(String username,String password);
}
