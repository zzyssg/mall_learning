package com.zzy.malladmin.dao;

import com.zzy.malladmin.mbg.model.PmsProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName PmsProductDao
 * @Author ZZy
 * @Date 2023/10/29 20:39
 * @Description
 * @Version 1.0
 */
@Repository
public interface PmsProductDao {

    int insertList(List<PmsProduct> productList);



}
