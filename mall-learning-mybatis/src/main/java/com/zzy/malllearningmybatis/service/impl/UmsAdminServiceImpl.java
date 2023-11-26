package com.zzy.malllearningmybatis.service.impl;



import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zzy.malllearningmybatis.dto.UmsAdminRoleCountDto;
import com.zzy.malllearningmybatis.mbg.mapper.UmsAdminMapper;
import com.zzy.malllearningmybatis.mbg.model.UmsAdmin;
import com.zzy.malllearningmybatis.mbg.model.UmsAdminExample;
import com.zzy.malllearningmybatis.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    @Autowired
    UmsAdminMapper umsAdminMapper;

    
    @Override
    public UmsAdmin selectById(Long id) {
        return umsAdminMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UmsAdmin> selectPage(int pageNum, int pageSize) {
         PageHelper.startPage(pageNum, pageSize);
        return umsAdminMapper.selectByExample(new UmsAdminExample());
    }

    @Override
    public List<UmsAdmin> listByNameAndStatus(int pageNum, int pageSize, String username, List<Integer> status) {
        //构造条件器
        PageHelper.startPage(pageNum, pageSize);
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = umsAdminExample.createCriteria();
        if (StrUtil.isNotEmpty(username)) {
            criteria.andUsernameEqualTo(username);
        }
        criteria.andStatusIn(status);
        umsAdminExample.setOrderByClause("create_time desc");
        return umsAdminMapper.selectByExample(umsAdminExample);
    }

    @Override
    public List<UmsAdmin> subList(Long roleId) {
        List<UmsAdmin> umsAdmins = umsAdminMapper.subList(roleId);
        return umsAdmins;
    }

    @Override
    public List<UmsAdminRoleCountDto> listRoleCount() {
        return umsAdminMapper.listRoleCount();
    }

    @Override
    public int deleteUserByName(String name) {
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(name);
        return  umsAdminMapper.deleteByExample(umsAdminExample);

    }

    @Override
    public int deleteUserByNameAndNickName(String name, String nickName) {
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(name);
        umsAdminExample.createCriteria().andNickNameEqualTo(nickName);
        int i = umsAdminMapper.deleteByExample(umsAdminExample);
        return i;
    }

    @Override
    public int updateStatusByExample(int status, List<Integer> roleId) {
        return 0;
    }
}
