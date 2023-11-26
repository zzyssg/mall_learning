package com.zzy.malladmin.dao;

import com.zzy.malladmin.mbg.model.UmsAdmin;
import com.zzy.malladmin.mbg.model.UmsRole;
import com.zzy.malladmin.model.UmsAdminRoleRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UmsAdminDao
 * @Author ZZy
 * @Date 2023/10/14 23:28
 * @Description
 * @Version 1.0
 */
@Repository
public interface UmsAdminDao {

    List<UmsAdmin> getByUsername(String username);

    int insertList(@Param("list") List<UmsAdminRoleRelation> adminRoleList);

    List<UmsRole> selectRoleList(Long adminId);
}
