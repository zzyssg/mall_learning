package com.zzy.malladmin.service;

import com.zzy.malladmin.mbg.model.UmsResource;
import com.zzy.malladmin.mbg.model.UmsRole;

import javax.management.relation.Role;
import java.util.List;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-12-31
 */
public interface TestUmsService {

    List<UmsResource> testUmsResource(Long adminId);

    List<UmsRole> testRole(Long roleId);

}
