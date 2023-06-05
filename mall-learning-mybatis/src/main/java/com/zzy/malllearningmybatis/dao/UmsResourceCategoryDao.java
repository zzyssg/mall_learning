package com.zzy.malllearningmybatis.dao;

import com.zzy.malllearningmybatis.domain.UmsResourceCategoryExt;
import com.zzy.malllearningmybatis.domain.UmsResourceExt;
import com.zzy.malllearningmybatis.model.UmsResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmsResourceCategoryDao {

    UmsResourceCategoryExt selectUmsResourceCategoryExtByCategoryId(Long categoryId);
    UmsResourceCategoryExt selectUmsResourceCategoryExtByCategoryId2(Long categoryId);


}
