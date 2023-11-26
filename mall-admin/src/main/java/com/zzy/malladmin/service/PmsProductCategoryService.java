package com.zzy.malladmin.service;

import com.zzy.malladmin.dto.PmsProductCategoryWithChildren;
import com.zzy.malladmin.mbg.model.PmsProductCategory;

import java.util.List;

/**
 * @ClassName PmsProductCategoryService
 * @Author ZZy
 * @Date 2023/11/12 20:17
 * @Description
 * @Version 1.0
 */
public interface PmsProductCategoryService {

    int create(PmsProductCategory productCategory);


    int update(Long id, PmsProductCategory pmsProductCategory);

    List<PmsProductCategory> listByParentId(Long parentId, Integer pageNum, Integer pageSize);

    PmsProductCategory getInfo(Long id);

    int delete(Long id);

    //树形结构
    List<PmsProductCategoryWithChildren> listWithChildren();

}
