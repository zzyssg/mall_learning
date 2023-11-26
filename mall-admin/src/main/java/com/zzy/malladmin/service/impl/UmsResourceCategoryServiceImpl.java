package com.zzy.malladmin.service.impl;

import com.zzy.malladmin.mbg.mapper.UmsResourceCategoryMapper;
import com.zzy.malladmin.mbg.model.UmsResourceCategory;
import com.zzy.malladmin.mbg.model.UmsResourceCategoryExample;
import com.zzy.malladmin.service.UmsResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName UmsResourceCategoryServiceImpl
 * @Author ZZy
 * @Date 2023/10/15 19:03
 * @Description
 * @Version 1.0
 */
@Service
public class UmsResourceCategoryServiceImpl implements UmsResourceCategoryService {


    @Autowired
    UmsResourceCategoryMapper umsResourceCategoryMapper;


    @Override
    public int add(UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setCreateTime(new Date());
        int count = umsResourceCategoryMapper.insert(umsResourceCategory);
        return count;
    }

    @Override
    public int update(Long id, UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setId(id);
        int count = umsResourceCategoryMapper.updateByPrimaryKeySelective(umsResourceCategory);
        return count;
    }

    @Override
    public List<UmsResourceCategory> listAll() {
        UmsResourceCategoryExample example = new UmsResourceCategoryExample();
        example.setOrderByClause("sort desc");
        return umsResourceCategoryMapper.selectByExample(example);
    }

    @Override
    public int delete(Long id) {
        return umsResourceCategoryMapper.deleteByPrimaryKey(id);
    }
}
