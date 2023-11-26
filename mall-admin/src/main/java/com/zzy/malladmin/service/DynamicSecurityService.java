package com.zzy.malladmin.service;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * @ClassName DynamicSecurityService
 * @Author ZZy
 * @Date 2023/10/22 17:17
 * @Description
 * @Version 1.0
 */
public interface DynamicSecurityService {
    /**
     * String :资源路径
     * ConfigAttribute ： security权限对象
     * @return
     */
    Map<String, ConfigAttribute> loadDataSource();

}
