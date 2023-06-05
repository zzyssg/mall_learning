package com.zzy.malllearningmybatis.dao;

import com.zzy.malllearningmybatis.domain.UmsResourceExt;
import com.zzy.malllearningmybatis.model.UmsResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmsResourceDao {

    UmsResourceExt selectUmsResourceExtByResourceId(Long id);
    UmsResourceExt selectUmsResourceExtByResourceId2(Long id);

    List<UmsResource> pageSearchByCategoryId(int categoryId);



}
