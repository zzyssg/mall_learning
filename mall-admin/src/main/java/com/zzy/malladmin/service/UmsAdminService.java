package com.zzy.malladmin.service;

import com.zzy.malladmin.dto.UmsAdminLoginParam;
import com.zzy.malladmin.dto.UpdatePasswordParam;
import com.zzy.malladmin.mbg.model.UmsAdmin;
import com.zzy.malladmin.mbg.model.UmsResource;
import com.zzy.malladmin.mbg.model.UmsRole;

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

    UmsAdmin getAdminByUsername(String username);

    List<UmsRole> getRole(Long adminId);

    int update(Long id, UmsAdmin umsAdmin);

    int updatePassword(UpdatePasswordParam updatePasswordwordParam);

    int updateRole(Long adminId, List<Integer> roleIds);

    List<UmsRole> getRoleList(Long adminId);
}
