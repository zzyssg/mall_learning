package com.zzy.malladmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzy.malladmin.dao.UmsRoleDao;
import com.zzy.malladmin.mbg.mapper.UmsRoleMapper;
import com.zzy.malladmin.mbg.model.UmsMenu;
import com.zzy.malladmin.mbg.model.UmsResource;
import com.zzy.malladmin.mbg.model.UmsRole;
import com.zzy.malladmin.mbg.model.UmsRoleExample;
import com.zzy.malladmin.service.UmsRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        return null;
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        //构造删除构造器
        
        //使用mapper删除
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
        return null;
    }

    @Override
    public List<UmsResource> listUmsResource(Long roleId) {
        return null;
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
        int count ;
        umsRole.setCreateTime(new Date());
        try {
            count = umsRoleMapper.insertSelective(umsRole);
        } catch (Exception e) {
            e.getMessage();
            count = -1;
        }
        return count;
    }

    @Override
    public int update(UmsRole umsRole) {
        int count ;
        try {
            count = umsRoleMapper.updateByPrimaryKeySelective(umsRole);
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
}
