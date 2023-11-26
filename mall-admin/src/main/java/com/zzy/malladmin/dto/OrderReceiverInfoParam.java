package com.zzy.malladmin.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: mall_learning
 * @description: 订单收货人信息
 * @author: zzy
 * @create: 2023-11-26
 */
@Getter
@Setter
public class OrderReceiverInfoParam {
    private Long orderId;
    private Integer status;
    //发票抬头
    private String billHeader;
    //发票内容
    private String billContent;
    private String billReceiverPhone;
    private String billReceiverAddress;
    private String billReceiverEmail;
    //收货人信息
    private String receiverName;
    private String receiverPhone;
    private String receiverPostCode;
    private String receiverProvince;
    private String receiverCity;
    private String receiverRegion;
    private String receiverDetailAddress;
    //备注
    private String note;

}