package com.zzy.malladmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-11-26
 */
@Data
public class OmsOrderReturnApplyQueryParam {

    //服务单号
    private Long id;
    //处理状态： 0-待处理 1-退货中 2-已完成 3-已拒绝
    private Integer status;
    @ApiModelProperty(value = "收货人姓名/号码")
    private String receiverKeyword;
    //申请时间
    private String creatTime;
    //操作人员
    private String handleMan;
    @ApiModelProperty(value = "处理时间")
    private String handleTime;


}