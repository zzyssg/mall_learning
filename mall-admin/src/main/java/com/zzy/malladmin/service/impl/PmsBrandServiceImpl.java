/**
 * @program: mall_learning-main
 * @description:
 * @author: zzy
 * @create: 2023-11-19
 */
package com.zzy.malladmin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zzy.malladmin.dto.PmsBrandParam;
import com.zzy.malladmin.mbg.mapper.PmsBrandMapper;
import com.zzy.malladmin.mbg.mapper.PmsProductMapper;
import com.zzy.malladmin.mbg.model.PmsBrand;
import com.zzy.malladmin.mbg.model.PmsBrandExample;
import com.zzy.malladmin.mbg.model.PmsProduct;
import com.zzy.malladmin.mbg.model.PmsProductExample;
import com.zzy.malladmin.service.PmsBrandService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    @Autowired
    PmsBrandMapper brandMapper;
    @Autowired
    PmsProductMapper productMapper;

    @Override
    public int create(PmsBrandParam brandParam) {
        PmsBrand brand = new PmsBrand();
        BeanUtils.copyProperties(brandParam, brand);
        int count = brandMapper.insertSelective(brand);
        return count;
    }

    @Override
    public int delete(Long id) {
        return brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Long id, PmsBrandParam brandParam) {
        PmsBrand brand = new PmsBrand();
        brand.setId(id);
        BeanUtils.copyProperties(brandParam, brand);
        int count = brandMapper.updateByPrimaryKey(brand);
        //其他表，是否有相关字段：product的品牌名
        PmsProduct product = new PmsProduct();
        product.setBrandName(brand.getName());
        PmsProductExample productExample = new PmsProductExample();
        productExample.createCriteria().andBrandIdEqualTo(id);
        productMapper.updateByExample(product, productExample);
        return count;
    }

    @Override
    public List<PmsBrand> list(String keyword,Integer showStatus, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsBrandExample example = new PmsBrandExample();
        example.setOrderByClause("sort desc");
        PmsBrandExample.Criteria criteria = example.createCriteria();
        if (!StrUtil.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        if (showStatus != null) {
            criteria.andShowStatusEqualTo(showStatus);
        }
        return brandMapper.selectByExample(example);
    }

    @Override
    public List<PmsBrand> listAll() {
        PmsBrandExample example = new PmsBrandExample();
        return brandMapper.selectByExample(example);
    }

    @Override
    public PmsBrand getItem(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        PmsBrandExample example = new PmsBrandExample();
        example.createCriteria().andIdIn(ids);
        return brandMapper.deleteByExample(example);
    }

    @Override
    public int updateFactoryStatus(List<Long> ids, Integer status) {
        PmsBrand brand = new PmsBrand();
        brand.setFactoryStatus(status);
        PmsBrandExample example = new PmsBrandExample();
        example.createCriteria().andIdIn(ids);
        return brandMapper.updateByExampleSelective(brand, example);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer status) {
        PmsBrand brand = new PmsBrand();
        brand.setShowStatus(status);
        PmsBrandExample example = new PmsBrandExample();
        example.createCriteria().andIdIn(ids);
        return brandMapper.updateByExample(brand, example);
    }
}