package com.zzy.malladmin.service.impl;

import com.zzy.malladmin.dao.UmsAdminRoleRelationDao;
import com.zzy.malladmin.dao.UmsRoleDao;
import com.zzy.malladmin.mbg.model.UmsResource;
import com.zzy.malladmin.mbg.model.UmsRole;
import com.zzy.malladmin.service.TestUmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-12-31
 */
@Service
public class TestUmsServiceImpl implements TestUmsService {

    @Autowired
    UmsAdminRoleRelationDao umsAdminRoleRelationDao;

    @Autowired
    UmsRoleDao roleDao;

    @Override
    public List<UmsResource> testUmsResource(Long adminId) {
        return umsAdminRoleRelationDao.getResourceList(adminId);
    }

    @Override
    public List<UmsRole> testRole(Long roleId) {
        return roleDao.getAllRole();
    }

}