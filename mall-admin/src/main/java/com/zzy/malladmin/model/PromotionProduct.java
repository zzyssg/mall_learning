package com.zzy.malladmin.model;

import com.zzy.malladmin.mbg.model.PmsProduct;
import com.zzy.malladmin.mbg.model.PmsProductFullReduction;
import com.zzy.malladmin.mbg.model.PmsProductLadder;
import com.zzy.malladmin.mbg.model.PmsSkuStock;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @program: mall_learning
 * @description: 促销商品信息：包含打折、促销、积分满减优惠以及sku
 * @author: zzy
 * @create: 2023-12-16
 */
@Getter
@Setter
public class PromotionProduct extends PmsProduct {

    //商品库存信息
    private List<PmsSkuStock> skuStockList;
    //打折信息
    private List<PmsProductLadder> productLadderList;
    //满减信息
    private List<PmsProductFullReduction> fullReductionList;

}