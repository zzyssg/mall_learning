package com.zzy.malladmin.dto;

import com.zzy.malladmin.mbg.model.PmsProductAttributeCategory;
import com.zzy.malladmin.mbg.model.PmsProductCategory;
import lombok.Data;

import java.util.List;

/**
 * @ClassName PmsProductCategoryWithChildren
 * @Author ZZy
 * @Date 2023/11/12 20:34
 * @Description
 * @Version 1.0
 */
@Data
public class PmsProductCategoryWithChildren extends PmsProductAttributeCategory {

    List<PmsProductCategoryWithChildren> children;


}
