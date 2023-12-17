package com.zzy.malladmin.dto;

import com.zzy.malladmin.mbg.model.OmsCartItem;
import com.zzy.malladmin.mbg.model.PmsProduct;
import com.zzy.malladmin.mbg.model.PmsProductAttribute;
import com.zzy.malladmin.mbg.model.PmsSkuStock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-11-29
 */
public class CartProduct extends PmsProduct {

    @Getter
    @Setter
    @ApiModelProperty("商品属性列表")
    private List<PmsProductAttribute> productAttributeList;

    @Getter
    @Setter
    @ApiModelProperty("商品sku列表")
    private List<PmsSkuStock> skuStockList;


}