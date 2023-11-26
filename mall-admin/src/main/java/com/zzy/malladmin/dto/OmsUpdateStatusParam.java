package com.zzy.malladmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-11-26
 */
@Data
public class OmsUpdateStatusParam {

    @ApiModelProperty(value = "服务单号")
    private Long id;
    @ApiModelProperty(value = "收货地址关联ID")
    private Long companyAddressId;
    @ApiModelProperty(value = "默认退款金额")
    private BigDecimal returnAmount;
    @ApiModelProperty(value = "处理备注")
    private String handleNote;
    @ApiModelProperty(value = "处理人")
    private String handleMan;
    @ApiModelProperty(value = "收货备注")
    private String receiveNote;
    @ApiModelProperty(value = "收货人")
    private String receiverMan;
    @ApiModelProperty(value = "申请状态: 1-退货中 2-已完成 3-已拒绝")
    private Integer status;

}