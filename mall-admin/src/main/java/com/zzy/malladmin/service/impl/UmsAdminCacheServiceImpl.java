package com.zzy.malladmin.service.impl;

import com.zzy.malladmin.mbg.model.UmsAdmin;
import com.zzy.malladmin.mbg.model.UmsResource;
import com.zzy.malladmin.service.RedisService;
import com.zzy.malladmin.service.UmsAdminCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @ClassName UmsAdminCacheServiceImpl
 * @Author ZZy
 * @Date 2023/10/25 15:29
 * @Description
 * @Version 1.0
 */
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {
    @Autowired
    private RedisService redisService;

    @Value("${redis.database}")
    private String REDIS_DATABASE;

    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;

    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    @Value("${redis.expire}")
    private Long REDIS_EXPIRE;


    @Override
    public void delAdmin(Long adminId) {

    }

    @Override
    public void delResourceList(Long adminId) {

    }

    @Override
    public void delResourceListByRole(Long roleId) {

    }

    @Override
    public void delResource(Long resourceId) {

    }

    @Override
    public void delResourceListByRoles(Long[] roleIds) {

    }

    @Override
    public UmsAdmin getAdmin(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
        return (UmsAdmin) redisService.get(key);
    }

    @Override
    public void setAdmin(UmsAdmin umsAdmin) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + umsAdmin.getUsername();
        redisService.set(key, umsAdmin,REDIS_EXPIRE);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + adminId;
        List<UmsResource> umsResourceList  = (List<UmsResource>) redisService.get(key);
        return umsResourceList;
    }

    @Override
    public void setResourceList(Long adminId, List<UmsResource> umsResourceList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + adminId;
        redisService.set(key, umsResourceList,REDIS_EXPIRE);
    }
}
