package com.zzy.malladmin.service;

import com.zzy.malladmin.mbg.model.UmsAdmin;
import com.zzy.malladmin.mbg.model.UmsResource;

import java.util.List;

/**
 * @ClassName UmsAdminCacheService
 * @Author ZZy
 * @Date 2023/10/25 15:16
 * @Description
 * @Version 1.0
 */
public interface UmsAdminCacheService {

    /**
     * 删除后台用户缓存
     * @param adminId
     */
    void delAdmin(Long adminId);

    void delResourceList(Long adminId);

    void delResourceListByRole(Long roleId);

    void delResource(Long resourceId);

    void delResourceListByRoles(Long[] roleIds);

    /**
     * 获取后台缓存用户信息
     * @param username
     * @return
     */
    UmsAdmin getAdmin(String username);

    /**
     * 缓存中设置后台用户信息
     * @param umsAdmin
     */
    void setAdmin(UmsAdmin umsAdmin);

    /**
     * 从缓存中查询资源列表
     * @param adminId
     * @return
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 在缓存中设置资源列表
     * @param adminId
     * @param umsResourceList
     */
    void setResourceList(Long adminId, List<UmsResource> umsResourceList);



}
