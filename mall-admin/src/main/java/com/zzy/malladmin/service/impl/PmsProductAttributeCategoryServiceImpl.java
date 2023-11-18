package com.zzy.malladmin.service.impl;

import com.zzy.malladmin.dao.PmsProductAttributeCategoryDao;
import com.zzy.malladmin.dto.PmsProductAttributeCategoryChildren;
import com.zzy.malladmin.mbg.mapper.PmsProductAttributeCategoryMapper;
import com.zzy.malladmin.mbg.mapper.PmsProductAttributeMapper;
import com.zzy.malladmin.mbg.model.PmsProductAttribute;
import com.zzy.malladmin.mbg.model.PmsProductAttributeCategory;
import com.zzy.malladmin.mbg.model.PmsProductAttributeCategoryExample;
import com.zzy.malladmin.service.PmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PmsProductAttributeCategoryServiceImpl
 * @Author ZZy
 * @Date 2023/11/15 22:30
 * @Description
 * @Version 1.0
 */
@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {

    @Autowired
    PmsProductAttributeCategoryMapper attributeCategoryMapper;

    @Autowired
    PmsProductAttributeCategoryDao attributeCategoryDao;

    @Override
    public PmsProductAttributeCategory getById(Long id) {
        return attributeCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int add(PmsProductAttributeCategory attributeCategory) {
        //TODO 新增属性类型时，相关表：attribute的属性类型名称-无
        return attributeCategoryMapper.insertSelective(attributeCategory);
    }

    @Override
    public int deleteById(Long id) {
        return attributeCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PmsProductAttributeCategory> listAll() {
        PmsProductAttributeCategoryExample example = new PmsProductAttributeCategoryExample();
        return attributeCategoryMapper.selectByExample(example);
    }

    @Override
    public int updateById(Long id, PmsProductAttributeCategory attributeCategory) {
        attributeCategory.setId(id);
        int count = attributeCategoryMapper.updateByPrimaryKeySelective(attributeCategory);
        return count;
    }

    @Override
    public List<PmsProductAttributeCategoryChildren> listWithChildren() {
        return attributeCategoryDao.getAttributeCategoryWithChildren();

    }

    public List<PmsProductAttributeCategory> test() {
        return attributeCategoryDao.test();
    }

}
