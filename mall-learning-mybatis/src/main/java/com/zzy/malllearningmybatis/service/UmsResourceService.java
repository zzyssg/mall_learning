package com.zzy.malllearningmybatis.service;

import com.github.pagehelper.PageInfo;
import com.zzy.malllearningmybatis.model.UmsResource;

import java.util.List;

public interface UmsResourceService {

    PageInfo<UmsResource> pageSearch(int pageNum, int pageSize, int categoryId);

}
