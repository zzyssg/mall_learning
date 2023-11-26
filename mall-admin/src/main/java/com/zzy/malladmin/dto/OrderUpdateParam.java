package com.zzy.malladmin.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: mall_learning
 * @description: 订单修改参数
 * @author: zzy
 * @create: 2023-11-26
 */
@Data
public class OrderUpdateParam {

    private Integer billType;

    private String billHeader;

    private String billContent;

    private String billReceiverPhone;

    private String billReceiverEmail;

    private String receiverName;

    private String receiverPhone;

    private String receiverPostCode;

    private String receiverProvince;

    private String receiverCity;

    private String receiverRegion;

    private String receiverDetailAddress;














}