package com.zzy.malladmin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zzy.malladmin.mbg.mapper.UmsResourceMapper;
import com.zzy.malladmin.mbg.model.UmsResource;
import com.zzy.malladmin.mbg.model.UmsResourceExample;
import com.zzy.malladmin.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName UmsResourceServiceImpl
 * @Author ZZy
 * @Date 2023/10/15 18:18
 * @Description
 * @Version 1.0
 */
@Service
public class UmsResourceServiceImpl  implements UmsResourceService {

    @Autowired
    UmsResourceMapper umsResourceMapper;

    @Override
    public int create(UmsResource umsResource) {
        umsResource.setCreateTime(new Date());
        return umsResourceMapper.insert(umsResource);
    }

    @Override
    public int update(Long id, UmsResource umsResource) {
        umsResource.setId(id);
        int count = umsResourceMapper.updateByPrimaryKeySelective(umsResource);
        //cache管理员 删除资源列表 TODO
        return count;
    }

    @Override
    public List<UmsResource> list(String name,String location,Long category, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UmsResourceExample umsResourceExample = new UmsResourceExample();
        UmsResourceExample.Criteria criteria = umsResourceExample.createCriteria();
        if (!StrUtil.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StrUtil.isEmpty(location)) {
            criteria.andUrlLike("%" + location + "%");
        }
        if (category != null) {
            criteria.andCategoryIdEqualTo(category);
        }
        List<UmsResource> umsResourceList = umsResourceMapper.selectByExample(umsResourceExample);
        return umsResourceList;
    }

    @Override
    public int delete(Long id) {
        return umsResourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public UmsResource getResource(Long id) {
        return umsResourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UmsResource> listAll() {
        return umsResourceMapper.selectByExample(new UmsResourceExample());
    }
}
