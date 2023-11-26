package com.zzy.malladmin.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName PmsProductQueryParam
 * @Author ZZy
 * @Date 2023/10/27 0:26
 * @Description
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
public class PmsProductQueryParam {

    @ApiModelProperty("上架状态")
    private Integer publishStatus;

    @ApiModelProperty("审核状态")
    private Integer verifyStatus;

    @ApiModelProperty("商品分类")
    private Long productCategoryId;

    @ApiModelProperty("商品品牌")
    private Long productBrandId;

    @ApiModelProperty("商品货号")
    private String productSn;

    @ApiModelProperty("商品名称")
    private String keyword;



}
