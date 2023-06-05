package com.zzy.malllearningmybatis.service;


import com.zzy.malllearningmybatis.dto.UmsAdminRoleCountDto;
import com.zzy.malllearningmybatis.mbg.model.UmsAdmin;

import java.util.List;

public interface UmsAdminService {

    UmsAdmin selectById(Long id);

    List<UmsAdmin> selectPage(int pageNum, int pageSize);

    List<UmsAdmin> listByNameAndStatus(int pageNum, int pageSize, String username, List<Integer> status);

    List<UmsAdmin> subList(Long roleId);

    List<UmsAdminRoleCountDto> listRoleCount();

    int deleteUserByName(String name);
    int deleteUserByNameAndNickName(String name,String nickName);

    int updateStatusByExample(int status,List<Integer> roleId);

}
