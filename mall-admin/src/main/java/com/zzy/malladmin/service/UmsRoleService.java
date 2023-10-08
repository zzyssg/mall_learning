package com.zzy.malladmin.service;

import com.zzy.malladmin.mbg.model.UmsMenu;
import com.zzy.malladmin.mbg.model.UmsResource;
import com.zzy.malladmin.mbg.model.UmsRole;

import java.util.List;

/**
 * @ClassName UmsRoleService
 * @Author ZZy
 * @Date 2023/10/4 14:02
 * @Description
 * @Version 1.0
 */
public interface UmsRoleService {


    //分页查询
    List<UmsRole> list(String keyword, Integer pageNum, Integer pageSize);


    //TODO 展示全部角色
    List<UmsRole> listAll();

    //TODO 批量删除角色-根据List<ID>
    void deleteBatch(List<Integer> ids);

    //TODO 根据adminId展示菜单id
    List<UmsMenu> searchMenuById(Long adminId);

    //TODO 查询资源—根据roleId
    List<UmsMenu> listUmsMenu(Long roleId);

    //TODO 查询菜单—根据roleId
    List<UmsResource> listUmsResource(Long roleId);

    //TODO 分配资源-根据roleId
    void allocateResource(Long roleId);

    //TODO 分配菜单-根据roleId
    void allocateMenu(Long roleId);

    //新增角色
    int insert(UmsRole umsRole);

    //更新角色
    int update(UmsRole umsRole);

    //删除角色
    void delete(Long id);


}
