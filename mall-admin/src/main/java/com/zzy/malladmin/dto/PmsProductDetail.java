package com.zzy.malladmin.dto;

import com.zzy.malladmin.mbg.model.*;
import lombok.Data;

import java.util.List;

/**
 * @program: mall_learning-main
 * @description: 编辑或者查看时的详细数据
 * @author: zzy
 * @create: 2023-11-19
 */
@Data
public class PmsProductDetail {

    private PmsProduct pmsProduct;

    private PmsBrand pmsBrand;

    private List<PmsProductAttribute> productAttributeList;

    //手动录入的商品属性与参数值
    private List<PmsProductAttributeValue> productAttributeValueList;

    private List<PmsSkuStock> stockList;

    private List<PmsProductLadder> ladderList;

    private List<PmsProductFullReduction> fullReductionList;

}