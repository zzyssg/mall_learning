package com.zzy.malladmin.dto;

import com.zzy.malladmin.mbg.model.OmsCartItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-11-29
 */
@Data
public class CartPromotionItem extends OmsCartItem {

    @ApiModelProperty("促销活动信息")
    private String promotionMessage;

    @ApiModelProperty("减少金额")
    private BigDecimal reduceAccount;

    @ApiModelProperty("剩余库存-锁定库存")
    private Integer realStock;

    @ApiModelProperty("获取的积分")
    private Integer integration;

    @ApiModelProperty("获取的成长值")
    private Integer growth;
}