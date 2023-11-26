package com.zzy.malladmin.service;

import com.zzy.malladmin.dto.PmsProductAttributeCategoryChildren;
import com.zzy.malladmin.mbg.model.PmsProductAttribute;
import com.zzy.malladmin.mbg.model.PmsProductAttributeCategory;

import java.util.List;

/**
 * @ClassName PmsProductAttributeCategoryService
 * @Author ZZy
 * @Date 2023/11/15 22:24
 * @Description
 * @Version 1.0
 */
public interface PmsProductAttributeCategoryService {


    PmsProductAttributeCategory getById(Long id);

    int add(PmsProductAttributeCategory attributeCategory);

    int deleteById(Long id);

    List<PmsProductAttributeCategory> listAll();

//    List<PmsProductAttributeCategoryNode> listQWithChildren();

    int updateById(Long id, PmsProductAttributeCategory attributeCategory);


    List<PmsProductAttributeCategoryChildren> listWithChildren();

    List<PmsProductAttributeCategory> test();
}
