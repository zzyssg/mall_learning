package com.zzy.malllearningmybatis.mbg.mapper;

import com.zzy.malllearningmybatis.dto.UmsAdminRoleCountDto;
import com.zzy.malllearningmybatis.mbg.model.UmsAdmin;
import com.zzy.malllearningmybatis.mbg.model.UmsAdminExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface UmsAdminMapper {
    long countByExample(UmsAdminExample example);

    int deleteByExample(UmsAdminExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsAdmin record);

    int insertSelective(UmsAdmin record);

    List<UmsAdmin> selectByExample(UmsAdminExample example);

    UmsAdmin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsAdmin record, @Param("example") UmsAdminExample example);

    int updateByExample(@Param("record") UmsAdmin record, @Param("example") UmsAdminExample example);

    int updateByPrimaryKeySelective(UmsAdmin record);

    int updateByPrimaryKey(UmsAdmin record);

    List<UmsAdmin> subList(@Param("roleId") Long roleId);

    List<UmsAdminRoleCountDto> listRoleCount();
}