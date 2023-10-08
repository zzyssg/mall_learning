package com.zzy.malladmin.service;

import com.zzy.malladmin.dto.UmsAdminLoginParam;
import com.zzy.malladmin.mbg.model.UmsAdmin;
import com.zzy.malladmin.mbg.model.UmsResource;

import java.util.List;

/**
 * @ClassName UmsAdminService
 * @Author ZZy
 * @Date 2023/9/29 12:28
 * @Description
 * @Version 1.0
 */
public interface UmsAdminService {


    List<UmsAdmin> list(String keywords, Integer pageNum, Integer pageSize);

    UmsAdmin getItem(Long id);

    Integer add(UmsAdmin umsAdmin);

    Integer update(UmsAdmin umsAdmin);

    Integer deleteById(Long id);

    int register(UmsAdmin umsAdmin);

    String login(UmsAdminLoginParam umsAdminService);

    UmsAdmin geUserByUsername(String username);

    List<UmsResource> getResourceList(Long adminId);

    String refreshToken(String token);
}
