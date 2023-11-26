package com.zzy.malladmin.service;

import com.zzy.malladmin.mbg.model.UmsResourceCategory;

import java.util.List;

/**
 * @ClassName UmsResourceCategoryService
 * @Author ZZy
 * @Date 2023/10/15 19:02
 * @Description
 * @Version 1.0
 */
public interface UmsResourceCategoryService {

    int add(UmsResourceCategory umsResourceCategory);


    int update(Long id, UmsResourceCategory umsResourceCategory);

    List<UmsResourceCategory> listAll();

    int delete(Long id);
}
