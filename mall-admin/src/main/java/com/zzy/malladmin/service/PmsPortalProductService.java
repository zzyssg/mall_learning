package com.zzy.malladmin.service;

import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.dto.PmsProductCategoryWithChildren;
import com.zzy.malladmin.dto.PmsProductDetail;
import com.zzy.malladmin.mbg.model.PmsProduct;

import java.util.List;

/**
 * @program: mall_learning-main
 * @description: 前台商品管理
 * @author: zzy
 * @create: 2023-11-19
 */
public interface PmsPortalProductService {

    List<PmsProductCategoryWithChildren> categoryTreeList();

    PmsProductDetail getDetail(Long productId);

    CommonPage<PmsProduct> search(String keyword, Long brandId,Long productCategoryId,Long sort,Integer pageNum, Integer pageSize);

}
