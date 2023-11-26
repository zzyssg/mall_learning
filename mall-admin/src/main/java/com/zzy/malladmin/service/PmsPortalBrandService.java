package com.zzy.malladmin.service;

import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.mbg.model.PmsBrand;
import com.zzy.malladmin.mbg.model.PmsProduct;

import java.util.List;

public interface PmsPortalBrandService {

    /**
     * 获取首页品牌推荐列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PmsBrand> recommendList(Integer pageNum, Integer pageSize);

    /**
     * 获取品牌详情
     * @param brandId
     * @return
     */
    PmsBrand detail(Long brandId);

    /**
     * 分页获取品牌相关产品
     * @param brandId
     * @param pageNum
     * @param pageSize
     * @return
     */
    CommonPage<PmsProduct> productList(Long brandId, Integer pageNum, Integer pageSize);

}
