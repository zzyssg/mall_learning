package com.zzy.malladmin.dto;

import com.zzy.malladmin.mbg.model.PmsProductAttribute;
import com.zzy.malladmin.mbg.model.PmsProductAttributeCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @ClassName PmsProductAttributeCategoryNode
 * @Author ZZy
 * @Date 2023/11/15 22:50
 * @Description
 * @Version 1.0
 */

public class PmsProductAttributeCategoryChildren extends PmsProductAttributeCategory {
    @Getter
    @Setter
    @ApiModelProperty(value = "属性列表")
    List<PmsProductAttribute> productAttributeList;

}
