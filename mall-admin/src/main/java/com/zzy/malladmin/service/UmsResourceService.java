package com.zzy.malladmin.service;

import com.zzy.malladmin.mbg.model.UmsResource;

import java.util.List;

/**
 * @ClassName UmsResourceService
 * @Author ZZy
 * @Date 2023/10/15 18:18
 * @Description
 * @Version 1.0
 */
public interface UmsResourceService {


    int create(UmsResource umsResource);

    int update(Long id, UmsResource umsResource);

    List<UmsResource> list(String name,String location,Long category, Integer pageNum, Integer pageSize);

    int delete(Long id);

    UmsResource getResource(Long id);

    List<UmsResource> listAll();
}
