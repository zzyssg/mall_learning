package com.zzy.malladmin.dto;

/**
 * @program: mall_learning
 * @description: 订单设置参数
 * @author: zzy
 * @create: 2023-11-26
 */
public class OrderSettingParam {

    //秒杀订单超时时间--单位：分
    private Integer flashOrderOver;
    //正常订单超时时间--单位：分
    private Integer normalOrderOver;
    //发货后自动确认收货时间--单位：天
    private Integer confirmOverTime;
    //自动完成交易时间--单位：天 不能申请售后
    private Integer finishOverTime;
    //订单完成后自动好评时间--单位：天
    private Integer commentOverTime;

}