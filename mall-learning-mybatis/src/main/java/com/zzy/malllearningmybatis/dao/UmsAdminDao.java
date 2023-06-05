package com.zzy.malllearningmybatis.dao;

import com.zzy.malllearningmybatis.model.UmsAdmin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmsAdminDao {

    UmsAdmin selectByIdSimple(Long id);

    int insert(UmsAdmin umsAdmin);
    int insert2(UmsAdmin umsAdmin);

    int insertBatch(@Param("umsAdmins") List<UmsAdmin> umsAdmins);

    int updateById(UmsAdmin umsAdmin);

    int updateByIdSelective(UmsAdmin umsAdmin);

    int deleteById(UmsAdmin umsAdmin);

    List<UmsAdmin> selectByUsernameAndEmailLike(UmsAdmin umsAdmin);
    List<UmsAdmin> selectByUsernameAndEmailLike2(UmsAdmin umsAdmin);
    List<UmsAdmin> selectByUsernameAndEmailLike3(UmsAdmin umsAdmin);

    List<UmsAdmin> selectByIds(@Param("ids") List<Long> ids);



}
