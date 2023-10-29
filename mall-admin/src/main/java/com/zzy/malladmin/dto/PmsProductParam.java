package com.zzy.malladmin.dto;

import com.zzy.malladmin.mbg.model.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName PmsProductParam
 * @Author ZZy
 * @Date 2023/10/26 22:29
 * @Description 继承商品类，作为请求的参数，用于数据传输
 * @Version 1.0
 */

@Data
public class PmsProductParam extends PmsProduct {

    @ApiModelProperty("商品阶梯价格设置")
    private List<PmsProductLadder> productLadderList;
    @ApiModelProperty("商品满减列表")
    private List<PmsProductFullReduction> productFullReductionList;
    @ApiModelProperty("商品会员价格列表")
    private List<PmsMemberPrice> memberPriceList;

    @ApiModelProperty("Sku库存列表")
    private List<PmsSkuStock> skuStockList;

    @ApiModelProperty("商品参数及自定义规格属性")
    private List<PmsProductAttributeValue> productAttributeValueList;

    //专题和商品关系

    //优选专区和商品的关系





}
