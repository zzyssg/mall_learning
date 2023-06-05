package com.zzy.malllearningmybatis.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.malllearningmybatis.dao.UmsResourceDao;
import com.zzy.malllearningmybatis.model.UmsResource;
import com.zzy.malllearningmybatis.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UmsResourceServiceImpl implements UmsResourceService {

    @Autowired
    UmsResourceDao umsResourceDao;

    @Override
    public PageInfo<UmsResource> pageSearch(int pageNum, int pageSize, int categoryId) {
        PageHelper.startPage(pageNum, pageSize);
        List<UmsResource> umsResources = umsResourceDao.pageSearchByCategoryId(categoryId);
        PageInfo<UmsResource> result = new PageInfo<>(umsResources);
        return result;
    }
}
