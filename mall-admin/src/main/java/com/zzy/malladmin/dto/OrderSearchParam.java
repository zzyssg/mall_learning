package com.zzy.malladmin.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: mall_learning
 * @description: 分页查询订单参数
 * @author: zzy
 * @create: 2023-11-26
 */
@Data
public class OrderSearchParam {


    //订单编号
    private String orderSn;

    //订单类型 0-正常 1-秒杀
    private Integer orderType;

    //订单状态 0-待付款 1-待发货 2-已发货 3-已完成 4-已关闭 5-无效订单
    private Integer status;

    //收货人姓名
    private String receiverName;

    //订单来源：0-PC订单 1-App订单
    private Integer sourceType;

    //订单提交时间
    private String createTime;




}