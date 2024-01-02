package com.zzy.malladmin.dao;

import com.zzy.malladmin.mbg.model.UmsResource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UmsAdminRoleRelationDao
 * @Author ZZy
 * @Date 2023/10/6 18:07
 * @Description
 * @Version 1.0
 */
@Mapper
public interface UmsAdminRoleRelationDao {

    List<UmsResource> getResourceList(Long adminId);

}
