package com.zzy.malladmin.mbg.mapper;

import com.zzy.malladmin.mbg.model.UmsRoleMenuRelation;
import com.zzy.malladmin.mbg.model.UmsRoleMenuRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UmsRoleMenuRelationMapper {
    long countByExample(UmsRoleMenuRelationExample example);

    int deleteByExample(UmsRoleMenuRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsRoleMenuRelation record);

    int insertSelective(UmsRoleMenuRelation record);

    List<UmsRoleMenuRelation> selectByExample(UmsRoleMenuRelationExample example);

    UmsRoleMenuRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsRoleMenuRelation record, @Param("example") UmsRoleMenuRelationExample example);

    int updateByExample(@Param("record") UmsRoleMenuRelation record, @Param("example") UmsRoleMenuRelationExample example);

    int updateByPrimaryKeySelective(UmsRoleMenuRelation record);

    int updateByPrimaryKey(UmsRoleMenuRelation record);
}