package com.zzy.malladmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName PmsProductResult
 * @Author ZZy
 * @Date 2023/11/11 19:57
 * @Description
 * @Version 1.0
 */
public class PmsProductResult extends PmsProductParam {

    @Getter
    @Setter
    @ApiModelProperty(value = "父级分类ID")
    private Long cateParentId;

}
