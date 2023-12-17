package com.zzy.malladmin.dao;

import com.zzy.malladmin.model.PromotionProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-12-16
 */
public interface PortalProductDao {

    List<PromotionProduct> getPromotionProductList(@Param("ids") Set<Long> ids);

}
