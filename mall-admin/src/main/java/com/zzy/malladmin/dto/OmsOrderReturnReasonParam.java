package com.zzy.malladmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-11-27
 */
@Data
public class OmsOrderReturnReasonParam {

    @ApiModelProperty(value="退货类型")
    private String name;

    private Integer sort;

    @ApiModelProperty(value="状态：0->不启用；1->启用")
    private Integer status;

    @ApiModelProperty(value="添加时间")
    private Date createTime;


}