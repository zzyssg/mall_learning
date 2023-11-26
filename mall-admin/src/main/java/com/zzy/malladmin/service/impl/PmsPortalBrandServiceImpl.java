package com.zzy.malladmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.dao.HomeDao;
import com.zzy.malladmin.mbg.mapper.PmsBrandMapper;
import com.zzy.malladmin.mbg.mapper.PmsProductMapper;
import com.zzy.malladmin.mbg.model.PmsBrand;
import com.zzy.malladmin.mbg.model.PmsProduct;
import com.zzy.malladmin.mbg.model.PmsProductExample;
import com.zzy.malladmin.service.PmsPortalBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: mall_learning-main
 * @description: 首页品牌相关
 * @author: zzy
 * @create: 2023-11-19
 */
@Service
public class PmsPortalBrandServiceImpl implements PmsPortalBrandService{

    @Autowired
    PmsBrandMapper brandMapper;

    @Autowired
    HomeDao homeDao;

    @Autowired
    PmsProductMapper productMapper;

    @Override
    public List<PmsBrand> recommendList(Integer pageNum, Integer pageSize) {
        int limit = pageSize;
        int offset = (pageNum - 1) * pageSize;
        return homeDao.getRecommendBrandList(offset, limit);
    }

    @Override
    public PmsBrand detail(Long brandId) {
        return brandMapper.selectByPrimaryKey(brandId);
    }

    @Override
    public CommonPage<PmsProduct> productList(Long brandId, Integer pageNum, Integer pageSize) {
        //PmsProductExample
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample productExample = new PmsProductExample();
        //未删除、已发布
        productExample.createCriteria().andBrandIdEqualTo(brandId).andDeleteStatusEqualTo(0).andPublishStatusEqualTo(1);
        List<PmsProduct> pmsProducts = productMapper.selectByExample(productExample);
        return CommonPage.restPage(pmsProducts);
    }
}