package com.zzy.malladmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzy.malladmin.dao.UmsRoleDao;
import com.zzy.malladmin.dao.UmsRoleMenuRelationDao;
import com.zzy.malladmin.mbg.mapper.UmsRoleMapper;
import com.zzy.malladmin.mbg.mapper.UmsRoleMenuRelationMapper;
import com.zzy.malladmin.mbg.mapper.UmsRoleResourceRelationMapper;
import com.zzy.malladmin.mbg.model.*;
import com.zzy.malladmin.service.UmsRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javafx.scene.input.KeyCode.L;

/**
 * @ClassName UmsRoleServiceImpl
 * @Author ZZy
 * @Date 2023/10/4 14:06
 * @Description
 * @Version 1.0
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UmsRoleService.class);

    @Autowired
    UmsRoleMapper umsRoleMapper;

    @Autowired
    UmsRoleMenuRelationMapper umsRoleMenuRelationMapper;

    @Autowired
    UmsRoleMenuRelationDao umsRoleMenuRelationDao;

    @Autowired
    UmsRoleResourceRelationMapper umsRoleResourceRelationMapper;

    @Autowired
    UmsRoleDao umsRoleDao;


    @Override
    public List<UmsRole> list(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //TODO 判断keyword是否为空
        //构造条件构造器
        UmsRoleExample umsRoleExample = new UmsRoleExample();
        UmsRoleExample.Criteria criteria = umsRoleExample.createCriteria();
        criteria.andNameLike("%" + keyword + "%");
        //调用Mapper查询
        List<UmsRole> umsRoleList = umsRoleMapper.selectByExample(umsRoleExample);
        return umsRoleList;
    }

    @Override
    public List<UmsRole> listAll() {
        List<UmsRole> allRole = umsRoleDao.getAllRole();
        return allRole;
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        //TODO 删除关联表信息 ?  无需删除关联表信息  ，关联表查询时，先查询基础表还是关联表（关联表里的角色可能已经不存在）？
        if (ids == null || ids.size() == 0) {
            return -1;
        }
        //构造删除构造器
        UmsRoleExample umsRoleExample = new UmsRoleExample();
        umsRoleExample.createCriteria().andIdIn(ids);
        //使用mapper删除
        int count = umsRoleMapper.deleteByExample(umsRoleExample);
        return count;
    }


    //连表查询--
    @Override
    public List<UmsMenu> searchMenuById(Long adminId) {
        //adminId -- roleId -- menuId
        List<UmsMenu> menuList = umsRoleDao.getMenuList(adminId);
        return menuList;
    }

    @Override
    public List<UmsMenu> listUmsMenu(Long roleId) {
        List<UmsMenu> menuList = umsRoleDao.listUmsMenu(roleId);
        return menuList;
    }

    @Override
    public List<UmsResource> listUmsResource(Long roleId) {
        List<UmsResource> umsResourceList = umsRoleDao.listUmsResource(roleId);
        return umsResourceList;
    }

    @Override
    public void allocateResource(Long roleId) {

    }

    @Override
    public void allocateMenu(Long roleId) {

    }

    @Override
    public int insert(UmsRole umsRole) {
        //初始化内容
        int count;
        umsRole.setCreateTime(new Date());
        try {
            count = umsRoleMapper.insertSelective(umsRole);
        } catch (Exception e) {
            e.getMessage();
            count = -1;
        }
        return count;
    }

    /**
     * 根据id更新角色，如果关键信息未改变，无需执行
     *
     * @param id
     * @param umsRole
     * @return
     */
    @Override
    public int update(Long id, UmsRole umsRole) {
        int count;
        umsRole.setId(id);
        try {
            count = umsRoleMapper.updateByPrimaryKeySelective(umsRole);
            //TODO 更新cache
        } catch (Exception e) {
            e.getMessage();
            count = -1;
        }
        return count;
    }

    @Override
    public void delete(Long id) {
        try {
            umsRoleMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * 用户-角色中间表、角色-菜单中间表、
     *
     * @param userId
     * @return
     */
    @Override
    public List<UmsMenu> getMenuList(Long userId) {
        List<UmsMenu> menuList = umsRoleDao.getMenuList(userId);
        return menuList;
    }

    @Override
    public int add(UmsRole umsRole) {
        return umsRoleMapper.insert(umsRole);

    }

    @Override
    public int allocateMenu(Long roleId, List<Integer> menus) {
        int count = menus == null ? 0 : menus.size();
        if (count == 0) {
            return count;
        }
        // 删除菜单
        UmsRoleMenuRelationExample umsRoleMenuRelationExample = new UmsRoleMenuRelationExample();
        umsRoleMenuRelationExample.createCriteria().andRoleIdEqualTo(roleId);
        umsRoleMenuRelationMapper.deleteByExample(umsRoleMenuRelationExample);

        // 重新分配权限
        List<UmsRoleMenuRelation> newMenuList = new ArrayList<>();
        for (int i = 0; i < menus.size(); i++) {
            UmsRoleMenuRelation umsRoleMenuRelation = new UmsRoleMenuRelation();
            umsRoleMenuRelation.setRoleId((long) menus.get(i));
            newMenuList.add(umsRoleMenuRelation);
        }
        umsRoleMenuRelationDao.batchInsert(newMenuList);
        // TODO 向缓存中更新
        return count;

    }

    @Override
    public int allocateResource(Long roleId, List<Long> resources) {
        int count = resources == null ? 0 : resources.size();
        if (count == 0) {
            return count;
        }
        //删除资源
        UmsRoleResourceRelationExample umsRoleResourceRelationExample = new UmsRoleResourceRelationExample();
        umsRoleResourceRelationExample.createCriteria().andRoleIdEqualTo(roleId);
        umsRoleResourceRelationMapper.deleteByExample(umsRoleResourceRelationExample);
        //重新分配资源
        List<UmsRoleResourceRelation> list = new ArrayList<>();
        for (int i = 0; i < resources.size(); i++) {
            UmsRoleResourceRelation umsRoleResourceRelation = new UmsRoleResourceRelation();
            umsRoleResourceRelation.setRoleId(roleId);
            umsRoleResourceRelation.setResourceId(resources.get(i));
            list.add(umsRoleResourceRelation);
        }
        return count;
    }

    private List<Long> intToLong(List<Integer> menus) {
        List<Long> res = new ArrayList<>();
        for (int i = 0; i < menus.size(); i++) {
            res.add((long) menus.get(i));
        }
        return res;
    }
}
