package com.zzy.malladmin.dto;

import lombok.Data;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-11-26
 */
@Data
public class OrderDeliveryParam {

    //发货方式
    private String deliveryCompany;

    //发货单号
    private String deliverySn;

    //订单ID
    private Long orderId;

}