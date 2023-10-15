package com.zzy.malladmin.dao;

import com.zzy.malladmin.mbg.model.UmsRoleMenuRelation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UmsRoleMenuRelationDao
 * @Author ZZy
 * @Date 2023/10/15 13:19
 * @Description
 * @Version 1.0
 */
@Repository
public interface UmsRoleMenuRelationDao {

    int batchInsert(List<UmsRoleMenuRelation> umsRoleMenuRelationList);

}
