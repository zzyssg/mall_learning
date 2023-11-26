package com.zzy.malladmin.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @program: mall_learning
 * @description: 修改订单费用信息：打折、运费金额
 * @author: zzy
 * @create: 2023-11-26
 */
@Getter
@Setter
public class OrderMoneyInfoParam {

    //订单ID
    private Long orderId;

    //运费金额
    private BigDecimal freightAmount;

    //打折
    private BigDecimal discountAmount;

    //订单状态  2：已发货 3：已完成
    private Integer status;

}