package com.zzy.malladmin.dao;

import com.zzy.malladmin.mbg.model.UmsMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName RoleDao
 * @Author ZZy
 * @Date 2023/10/5 23:11
 * @Description
 * @Version 1.0
 */
@Mapper
public interface UmsRoleDao {

    /**
     * 根据roleId查询MenuLIst
     */
    List<UmsMenu> getMenuList(Long adminId);

}
