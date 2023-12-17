package com.zzy.malladmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: mall_learning
 * @description: 订单管理：创建新订单-请求参数
 * @author: zzy
 * @create: 2023-11-26
 */
@Data
public class OmsOrderParam {

    @ApiModelProperty("收货人地址")
    private Long memberReceiveAddressId;

    @ApiModelProperty("优惠券ID")
    private Long couponId;

    @ApiModelProperty("使用的积分数")
    private Integer userIntegration;

    @ApiModelProperty("支付方式")
    private Integer payType;

    @ApiModelProperty("被选中的购物车商品ID")
    private List<Long> cartIds;
}