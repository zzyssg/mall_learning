package com.zzy.malladmin.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName PmsSkuStockDao
 * @Author ZZy
 * @Date 2023/11/2 10:49
 * @Description
 * @Version 1.0
 */
@Repository
public interface PmsSkuStockDao {

    int insertList(List list);

    int replaceList(List list);

}
